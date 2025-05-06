import styles from '/src/css/alim.css?inline';
import { getAlimList } from '/src/userApi.js'

class AlimComponent extends HTMLElement {
    constructor() {
        super();
        // Shadow DOM 사용
        this.attachShadow({ mode: 'open' });
        // 알림 데이터 (실제로는 API 호출 등으로 가져올 수 있음)
        this.alimList = [];
    }

    async connectedCallback() {
        await this.loadAlimList();
        this.render();
        await this.setupEventListeners();
    }

    render() {
        this.shadowRoot.innerHTML = `
            <style>${ styles }</style>
            <button class="alim-button">
                🔔
                ${ this.alimList.length > 0 ? `<span class="notification-count">${ this.alimList.length }</span>` : '' }
            </button>
            
            <div class="alim-modal">
                <div class="alim-header">
                    <span>알림</span>
                    <button class="alim-close">✕</button>
                </div>
                <div class="alim-list">
                    ${ this.renderAlimList() }
                </div>
            </div>
        `;
    }

    renderAlimList() {
        if(this.alimList.length === 0) {
            return '<div class="alim-empty">새로운 알림이 없습니다.</div>';
        }

        return this.alimList.map(alim => `
            <div class="alim-item">
                <div class="alim-message">${ alim }</div>
            </div>
        `).join('');
    }

    async setupEventListeners() {
        const alimButton = this.shadowRoot.querySelector('.alim-button');
        const alimModal = this.shadowRoot.querySelector('.alim-modal');
        const closeButton = this.shadowRoot.querySelector('.alim-close');

        // 알림 버튼 클릭 이벤트
        alimButton.addEventListener('click', () => {
            alimModal.classList.toggle('show');
        });

        // 닫기 버튼 이벤트
        closeButton.addEventListener('click', () => {
            alimModal.classList.remove('show');
        });

        // 바깥 영역 클릭 시 모달 닫기
        document.addEventListener('click', (event) => {
            const path = event.composedPath();
            if(!path.includes(this)) {
                alimModal.classList.remove('show');
            }
        });
    }

    async loadAlimList() {
        this.alimList = await getAlimList();
    }
}

customElements.define('alim-component', AlimComponent);