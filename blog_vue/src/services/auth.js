import {API_BASE_URL} from '../../config'
export const refreshToken = async () => {
    try {
        // 로컬 스토리지에서 리프레시 토큰 가져오기
        const refreshToken = localStorage.getItem('refreshToken')

        if (!refreshToken) {
            throw new Error('리프레시 토큰이 존재하지 않습니다.')
        }

        // 서버에 리프레시 토큰으로 새 액세스 토큰 요청
        const response = await fetch(`${API_BASE_URL}/api/auth/refresh`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Authorization: refreshToken,
            },
        })

        if (!response.ok) {
            throw new Error('토큰 갱신에 실패했습니다.')
        }

        const data = await response.json()

        // 새로운 토큰을 로컬 스토리지에 저장
        localStorage.setItem('accessToken', data.accessToken)

        // 서버가 새 리프레시 토큰도 발급한 경우 저장
        if (data.refreshToken) {
            localStorage.setItem('refreshToken', data.refreshToken)
        }

        return data.accessToken
    } catch (error) {
        console.error('토큰 갱신 오류:', error)
        // 로그인 페이지로 리디렉션
        window.location.href = '/login'
        throw error
    }
}
