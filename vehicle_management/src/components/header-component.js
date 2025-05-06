import Cookies from "universal-cookie";
import "./alim-component.js";

class HeaderComponent extends HTMLElement {
    constructor() {
        super();
        this.cookies = new Cookies(null, { path: "/" });
        this.render();
        this.addEventListeners();
    }

    // JWT 토큰에서 정보 추출
    parseJwt(token) {
        try {
            const base64Url = token.split(".")[1];
            const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
            const jsonPayload = decodeURIComponent(
                atob(base64)
                    .split("")
                    .map(function (c) {
                        return "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2);
                    })
                    .join("")
            );

            return JSON.parse(jsonPayload);
        } catch(e) {
            return null;
        }
    }

    isLoggedIn() {
        return !!this.cookies.get("accessToken");
    }

    getUserRole() {
        const accessToken = this.cookies.get("accessToken");
        if(!accessToken) return null;

        // JWT 토큰에서 역할 정보 추출
        const decodedToken = this.parseJwt(accessToken);
        return decodedToken?.roleSet;
    }

    isAdmin() {
        const role = this.getUserRole();
        return role === "ADMIN" || (Array.isArray(role) && role.includes("ADMIN"));
    }

    logout() {
        this.cookies.remove("accessToken");
        this.cookies.remove("refreshToken");
        this.cookies.remove("mid");
        window.location.href = "/";
    }

    // 컴포넌트 렌더링
    render() {
        const isLoggedIn = this.isLoggedIn();
        const isAdmin = this.isAdmin();

        // 로그인/로그아웃 링크
        const loginLogoutLink = isLoggedIn
            ? `<li><a href="#" id="logoutBtn">로그아웃</a></li>`
            : `<li><a href="/login">로그인</a></li>`;

        // 관리자 전용 메뉴
        const adminMenu = isAdmin
            ? `<li><a href="/pages/admin/vehicles">(관리자) 차량정보 목록</a></li>`
            : "";

        // 로그인 후 모든 사용자에게 보이는 메뉴
        const userMenu = isLoggedIn
            ? `
                <li><a href="/pages/user/vehicles">소유차량</a></li>
                <li><a href="/pages/user/maintenances">정비일지 목록</a></li>
              `
            : "";

        const alimComponent = isLoggedIn ? `<li class="alim-li"><alim-component></alim-component></li>` : '';

        this.innerHTML = `
          <header>
            <h1><a href="/">차량 관리 시스템</a></h1>
            <nav>
              <ul>
                <li><a href="/">홈</a></li>
                ${ userMenu }
                ${ adminMenu }
                ${ loginLogoutLink }
                ${ alimComponent }
              </ul>
            </nav>
          </header>
        `;
    }

    // 이벤트 리스너 추가
    addEventListeners() {
        // 로그아웃 버튼 이벤트
        const logoutBtn = this.querySelector("#logoutBtn");
        if(logoutBtn) {
            logoutBtn.addEventListener("click", (e) => {
                e.preventDefault();
                this.logout();
            });
        }
    }
}

// 웹 컴포넌트 등록
customElements.define("header-component", HeaderComponent);
