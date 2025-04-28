import { getBrandList, getModelList, getTrimList, saveVehicle } from '/src/userApi.js'

class VehicleModal extends HTMLElement {
    constructor() {
        super();
        this.onSaveCallback = null;

        this.brands = [];
        this.models = [];
        this.trims = [];

        this.selectedTrim = null;
    }

    async connectedCallback() {
        this.renderInitialStructure();
        await this.loadBrands();
        await this.setupEventListeners();
    }

    async setupEventListeners() {
        this.querySelector('#brandList').addEventListener('change', (e) => {
            if(e.target.name === 'brand') {
                const brandIdx = e.target.value;
                this.selectedTrim = null;
                this.loadModels(brandIdx);
            }
        });

        this.querySelector('#modelList').addEventListener('change', (e) => {
            if(e.target.name === 'model') {
                const modelIdx = e.target.value;
                this.selectedTrim = null;
                this.loadTrims(modelIdx);
            }
        });

        this.querySelector('#trimList').addEventListener('change', (e) => {
            if(e.target.name === 'trim') {
                const trimIdx = e.target.value;
                this.selectedTrim = trimIdx;
            }
        });

        this.querySelector('#closeModalBtn').addEventListener('click', () => {
            this.close();
        });

        this.querySelector('#cancelModalBtn').addEventListener('click', () => {
            this.close();
        });

        this.querySelector('#saveModalBtn').addEventListener('click', async () => {
            const numberPlate = this.querySelector("#numberPlate").value;
            const kilometers = this.querySelector("#kilometers").value;

            if(!numberPlate || !kilometers || !this.selectedTrim) {
                alert('정보를 모두 입력해주세요');
                return;
            }

            await saveVehicle(numberPlate, kilometers, this.selectedTrim);
            this.onSaveCallback();
            this.close();
        });
    }

    async loadBrands() {
        try {
            this.brands = await getBrandList();
            this.renderBrands();
        } catch(error) {
            console.error('브랜드 로드 중 오류 발생:', error);
        }
    }

    async loadModels(brandIdx) {
        try {
            this.models = await getModelList(brandIdx);
            this.renderModels();
        } catch(error) {
            console.error('차량모델 로드 중 오류 발생:', error);
        }
    }

    async loadTrims(modelIdx) {
        try {
            this.trims = await getTrimList(modelIdx);
            this.renderTrims();
        } catch(error) {
            console.error('차량모델별 트림 로드 중 오류 발생:', error);
        }
    }

    renderBrands() {
        const brandListContainer = this.querySelector('#brandList');
        brandListContainer.innerHTML = '';

        this.brands.forEach(brand => {
            const brandItem = document.createElement('div');
            brandItem.className = 'radio-item';

            brandItem.innerHTML = `
                <input type="radio" name="brand" id="brand-${ brand.idx }" value="${ brand.idx }">
                <label for="brand-${ brand.idx }">${ brand.name }</label>
            `;

            brandListContainer.appendChild(brandItem);
        });
    }

    renderModels() {
        const modelListContainer = this.querySelector('#modelList');
        modelListContainer.innerHTML = '';

        this.models.forEach(model => {
            const modelItem = document.createElement('div');
            modelItem.className = 'radio-item';

            modelItem.innerHTML = `
                <input type="radio" name="model" id="model-${ model.idx }" value="${ model.idx }">
                <label for="model-${ model.idx }">${ model.name }</label>
            `;

            modelListContainer.appendChild(modelItem);
        });
    }

    renderTrims() {
        const trimListContainer = this.querySelector('#trimList');
        trimListContainer.innerHTML = '';

        this.trims.forEach(trim => {
            const trimItem = document.createElement('div');
            trimItem.className = 'radio-item';

            trimItem.innerHTML = `
                <input type="radio" name="trim" id="trim-${ trim.idx }" value="${ trim.idx }">
                <label for="trim-${ trim.idx }">${ trim.drivetrain }/${ trim.fuelType }/${ trim.transmission }</label>
            `;

            trimListContainer.appendChild(trimItem);
        });
    }

    open(onSave) {
        this.onSaveCallback = onSave;
        this.querySelector('#vehicleModal').style.display = 'block';
    }

    close() {
        this.classList.remove('active');
        document.body.style.overflow = '';

        this.querySelector('#numberPlate').value = '';
        this.querySelector('#kilometers').value = '';

        const radioInputs = this.querySelectorAll('input[type="radio"]');
        radioInputs.forEach(input => {
            input.checked = false;
        });

        this.selectedTrim = null;

        this.querySelector('#modelList').innerHTML = '';
        this.querySelector('#trimList').innerHTML = '';

    }

    renderInitialStructure() {
        this.innerHTML = `
            <div class="modal-content" id="vehicleModal">
                <div class="modal-header">
                    <h2>차량 정보 등록</h2>
                    <button id="closeModalBtn" class="close-btn">&times;</button>
                </div>
                <div class="modal-body">
                    <div>
                        <label for="numberPlate">차량번호</label>
                        <input type="text" id="numberPlate">
                    </div>
                    <div>
                        <label for="kilometers">키로수</label>
                        <input type="number" id="kilometers">
                    </div>
                    <div>
                        <h2>브랜드</h2>
                        <div id="brandList" class="radio-group"></div>
                    </div>
                    <div>
                        <h2>모델</h2>
                        <div id="modelList" class="radio-group"></div>
                    </div>
                    <div>
                        <h2>트림</h2>
                        <div id="trimList" class="radio-group"></div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button id="cancelModalBtn" class="secondary-btn">취소</button>
                    <button id="saveModalBtn" class="primary-btn">저장</button>
                </div>
            </div>
            <div class="modal-backdrop"></div>
    `;
    }

}

customElements.define('vehicle-modal-component', VehicleModal);