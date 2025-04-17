export function renderPagination(elementId, currentPage, totalPages, onPageClick) {
    const pagination = document.getElementById(elementId);
    pagination.innerHTML = '';

    if(totalPages <= 1) {
        return;
    }

    // 이전 페이지 버튼
    if(currentPage > 1) {
        const prevBtn = document.createElement('button');
        prevBtn.textContent = '이전';
        prevBtn.addEventListener('click', () => {
            onPageClick(currentPage - 1);
        });
        pagination.appendChild(prevBtn);
    }

    // 페이지 버튼들
    for(let i = 1; i <= totalPages; i++) {
        // 페이지 수가 많으면 일부만 표시 (예: 현재 페이지 주변 ±2개)
        if(totalPages <= 10 || (i >= currentPage - 2 && i <= currentPage + 2) || i === 1 || i === totalPages) {
            // 생략 표시 (...)
            if(i > 1 && i < currentPage - 2) {
                const ellipsis = document.createElement('span');
                ellipsis.textContent = '...';
                pagination.appendChild(ellipsis);
                i = currentPage - 3; // 건너뛰기
                continue;
            }

            if(i < totalPages && i > currentPage + 2) {
                const ellipsis = document.createElement('span');
                ellipsis.textContent = '...';
                pagination.appendChild(ellipsis);
                i = totalPages - 1;
                continue;
            }

            const button = document.createElement('button');
            button.textContent = i;
            if(i === currentPage) {
                button.disabled = true;
            }

            button.addEventListener('click', () => {
                if(i !== currentPage) {
                    onPageClick(i);
                }
            });

            pagination.appendChild(button);
        }
    }

    // 다음 페이지 버튼
    if(currentPage < totalPages) {
        const nextBtn = document.createElement('button');
        nextBtn.textContent = '다음';
        nextBtn.addEventListener('click', () => {
            onPageClick(currentPage + 1);
        });
        pagination.appendChild(nextBtn);
    }
}
