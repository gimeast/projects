import { saveSpec } from '/src/adminApi.js'

class VehicleModal extends HTMLElement {
    constructor() {
        super();
        this.requiredFields = ['brand', 'model', 'drivetrain', 'fuelType', 'transmission'];
        this.onSaveCallback = null; // 저장 성공 시 호출할 콜백 함수
    }

    connectedCallback() {
        this.innerHTML = `
    <div class="modal-background" id="vehicleModal">
      <div class="modal-container">
        <div class="modal-header">
          <h2>차량 정보 등록</h2>
          <button class="modal-close" id="modalClose">&times;</button>
        </div>
        <div class="modal-body">
          <!-- 브랜드 -->
          <div class="form-group">
            <label for="brandInput">브랜드</label>
            <div class="input-wrapper">
              <input type="text" id="brandInput">
            </div>
          </div>

          <!-- 모델 -->
          <div class="form-group">
            <label for="modelInput">모델</label>
            <div class="input-wrapper">
              <input type="text" id="modelInput">
            </div>
          </div>

          <!-- 구동방식 -->
          <div class="form-group">
            <label for="drivetrainInput">구동방식</label>
            <div class="input-wrapper">
              <input type="text" id="drivetrainInput">
            </div>
          </div>

          <!-- 연료타입 -->
          <div class="form-group">
            <label for="fuelTypeInput">연료타입</label>
            <div class="input-wrapper">
              <input type="text" id="fuelTypeInput">
            </div>
          </div>

          <!-- 미션타입 -->
          <div class="form-group">
            <label for="transmissionInput">미션타입</label>
            <div class="input-wrapper">
              <input type="text" id="transmissionInput">
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button class="btn-cancel" id="cancelBtn">취소</button>
          <button class="btn-save" id="saveVehicleBtn">저장</button>
        </div>
      </div>
    </div>
    `;

        this.setupEventListeners();
    }

    setupEventListeners() {
        this.querySelector('#modalClose').addEventListener('click', () => {
            this.close();
        });

        this.querySelector('#cancelBtn').addEventListener('click', () => {
            this.close();
        });

        this.querySelector('#saveVehicleBtn').addEventListener('click', () => {
            this.saveVehicleInfo();
        });
    }

    open(onSave) {
        this.onSaveCallback = onSave;
        this.querySelector('#vehicleModal').style.display = 'block';
    }

    close() {
        this.requiredFields.forEach(field => {
            this.querySelector(`#${ field }Input`).value = '';
        });
        this.querySelector('#vehicleModal').style.display = 'none';
    }

    async saveVehicleInfo() {
        try {
            this.requiredFields.forEach(field => {
                const inputValue = this.querySelector(`#${ field }Input`).value;
                if(inputValue.trim() === '') {
                    throw Error('모든 정보를 입력해주세요.');
                }
            });

            const brandName = this.querySelector('#brandInput').value;
            const modelName = this.querySelector('#modelInput').value;
            const drivetrain = this.querySelector('#drivetrainInput').value;
            const fuelType = this.querySelector('#fuelTypeInput').value;
            const transmission = this.querySelector('#transmissionInput').value;

            const data = await saveSpec(brandName, modelName, drivetrain, fuelType, transmission);
            alert(data);

            this.onSaveCallback();
            this.close();
        } catch(error) {
            alert(error.message);
        }
    }
}

customElements.define('vehicle-modal-component', VehicleModal);