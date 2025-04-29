import { getTrimPartsList, saveParts, getPartsList, saveTrimPartsList, deleteTrimParts } from '/src/adminApi.js'

class PartsModal extends HTMLElement {
    constructor() {
        super();
        this.trimIdx = null;
    }

    connectedCallback() {
        this.innerHTML = `
            <div id="partsModal" style="display: none;">
                <div class="parts-modal-background">
                    <div class="parts-modal-container">
                        <div class="parts-modal-header">
                            <h2>부품 정보</h2>
                            <button class="parts-modal-close" id="closePartsModal">&times;</button>
                        </div>
                        <div class="parts-modal-body">
                            <div class="">
                                <label for="partsName">부품 명</label>
                                <input id="partsName" class="" />
                                <button id="partsAddBtn">추가</button>
                            </div>
                            
                            <table class="parts-table">
                                <thead>
                                    <tr>
                                        <th>부품 명</th>
                                        <th>교체 주기</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody id="partsTableBody">
                                    <tr>
                                        <td colspan="2">부품을 선택해주세요</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="parts-modal-footer">
                            <button id="trimPartsBtn">저장</button>
                            <button class="parts-modal-btn" id="closePartsBtn">닫기</button>
                        </div>
                    </div>
                </div>
            </div>
        `;

        this.setupEventListeners();
    }

    setupEventListeners() {
        this.querySelector('#closePartsBtn').addEventListener('click', () => {
            this.close();
        });
        this.querySelector('#closePartsModal').addEventListener('click', () => {
            this.close();
        });
        this.querySelector('#partsAddBtn').addEventListener('click', () => {
            this.partsAdd();
        });
        this.querySelector('#trimPartsBtn').addEventListener('click', () => {
            this.saveTrimParts();
        });
        this.querySelector('#partsTableBody').addEventListener('click', (event) => {
            if(event.target.classList.contains('delete-parts-btn')) {
                const trimPartsIdx = parseInt(event.target.dataset.trimPartsIdx);
                this.deleteTrimPartsData(trimPartsIdx);
            }
        });

    }

    open(trimIdx) {
        this.trimIdx = trimIdx;
        this.querySelector('#partsModal').style.display = 'block';

        this.getParts();
        this.getTrimParts();
    }

    close() {
        this.trimIdx = null;
        this.querySelector('#partsModal').style.display = 'none';
    }

    async getParts() {
        try {
            return await getPartsList(this.trimIdx);
        } catch(error) {
            alert(error.message);
        }
    }

    async getTrimParts() {
        try {
            const partsData = await this.getParts();
            const trimPartsData = await getTrimPartsList(this.trimIdx);

            let result = '';
            partsData.forEach((parts) => {
                let replacementValue = '';

                const matchingTrimPart = trimPartsData.find(
                    trimPart => trimPart.partsDTO.idx === parts.idx
                );

                if(matchingTrimPart) {
                    replacementValue = matchingTrimPart.replacementInterval;
                }

                result += `<tr>
                               <td>${ parts.name }</td>
                               <td>
                                    <input type="number" class="parts-interval-input" 
                                         data-parts-idx="${ parts.idx }" 
                                         value="${ replacementValue }" />
                               </td>
                               <td>
                               ${ matchingTrimPart ? `<button class="delete-parts-btn"
                                                             data-trim-parts-idx="${ matchingTrimPart?.idx }">삭제</button>` : '' }
                               </td>
                            </tr>`;
            });

            this.querySelector('#partsTableBody').innerHTML = result;
        } catch(error) {
            alert(error.message);
        }
    }

    async saveTrimParts() {
        let dataList = [];

        try {
            const intervalInputs = this.querySelectorAll('.parts-interval-input');

            intervalInputs.forEach(input => {
                const partsIdx = parseInt(input.dataset.partsIdx)
                const replacementInterval = input.value.trim();

                if(replacementInterval !== '') {
                    const data = {
                        "trimIdx": this.trimIdx,
                        "partsIdx": partsIdx,
                        "replacementInterval": parseInt(replacementInterval)
                    };

                    dataList.push(data);
                }
            });

            const result = await saveTrimPartsList(dataList);
            alert(result);

            await this.getTrimParts();
        } catch(error) {
            alert(error.message);
        }
    }

    async partsAdd() {
        try {
            const partsName = this.querySelector('#partsName').value;

            if(!partsName) {
                alert('부품명을 입력하세요');
                return;
            }

            const data = await saveParts(partsName);
            alert(data);

            await this.getTrimParts();
        } catch(error) {
            alert(error.message);
        }
    }

    async deleteTrimPartsData(trimPartsIdx) {
        try {
            console.log('trimPartsIdx:', trimPartsIdx);
            const data = await deleteTrimParts(trimPartsIdx);
            alert(data);

            await this.getTrimParts();
        } catch(error) {
            alert(error.message);
        }
    }

}

customElements.define('parts-modal-component', PartsModal);