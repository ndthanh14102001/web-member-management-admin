document.addEventListener('DOMContentLoaded', function () {
    // Lấy tham chiếu đến form
    var form = document.getElementById('processingForm');

    // Thêm sự kiện submit cho form
    form.addEventListener('submit', function (event) {
        // Lấy giá trị của input-amount
        var inputAmount = document.getElementById("input-amount");

        // Kiểm tra nếu giá trị của inputAmount rỗng
        if (inputAmount.value.trim() === "") {
            // Đặt giá trị của inputAmount là không
            inputAmount.value = "0";
        }
    });
    var rows = document.querySelectorAll('table tbody tr');
    rows.forEach(function (row) {
        row.addEventListener('click', function () {
            var inputid = document.getElementById('input-id');

            var inputAmount = document.getElementById('input-amount');
            var cells = this.cells;
            inputid.value = cells[0].textContent.trim();
            var name = cells[1].textContent.trim();
            var select = document.querySelector('select[id="process-name"]');
            var options = select.querySelectorAll('option');
            options.forEach(function (option) {
                if (option.textContent.trim() === name) {
                    option.selected = true;
                }
            });
            document.querySelector('select[id="process-type"]').value = cells[2].textContent.trim();
            document.querySelector('input[id="process-date"]').value = cells[3].textContent.trim();
            var amount = cells[4].textContent.trim();
            if (!isNaN(amount)) {
                inputAmount.value = amount;
            } else {
                inputAmount.value = '0';
            }
            document.querySelector('select[id="process-status"]').value = (cells[5].textContent.trim() === 'Đã xử lý' ? '1' : '0');
        });
    });

    var selectTTXL = document.getElementById('ttxl');
    var selectHTXL = document.getElementById('htxl');

    function filterRows() {
        var selectedOptionTTXL = selectTTXL.selectedOptions[0];
        var selectedValueTTXL = selectedOptionTTXL.textContent.trim();

        var selectedOptionHTXL = selectHTXL.selectedOptions[0];
        var selectedValueHTXL = selectedOptionHTXL.textContent.trim();

        var rows = document.querySelectorAll('.content table tbody tr');

        // Kiểm tra nếu một trong hai select có giá trị là "Chọn" thì hiển thị tất cả các hàng
        if (selectedValueTTXL === 'Chọn' && selectedValueHTXL === 'Chọn') {
            rows.forEach(function (row) {
                row.style.display = ''; // Hiển thị hàng
            });
        } else {
            rows.forEach(function (row) {
                var cellTTXL = row.cells[5]; // Lấy ô chứa trạng thái xử lý
                var cellValueTTXL = cellTTXL.textContent.trim();

                var cellHTXL = row.cells[2]; // Lấy ô chứa hình thức xử lý
                var cellValueHTXL = cellHTXL.textContent.trim();

                // So sánh giá trị của cột "Trạng thái xử lý" với giá trị được chọn trong selectTTXL
                // và so sánh giá trị của cột "Hình thức xử lý" với giá trị được chọn trong selectHTXL
                if ((selectedValueTTXL === 'Chọn' || cellValueTTXL === selectedValueTTXL) && (selectedValueHTXL === 'Chọn' || cellValueHTXL === selectedValueHTXL)) {
                    row.style.display = ''; // Hiển thị hàng nếu giá trị trùng khớp hoặc nếu một trong hai select là "Chọn"
                } else {
                    row.style.display = 'none'; // Ẩn hàng nếu không trùng khớp
                }
            });
        }
    }

    selectTTXL.addEventListener('change', filterRows);
    selectHTXL.addEventListener('change', filterRows);

});
function setFormAction(action) {
    // Lấy ra form thông qua ID
    var form = document.getElementById('processingForm');
    // Thay đổi thuộc tính action của form
    form.action = action;
}
