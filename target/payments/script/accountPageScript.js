function validateEditAccountForm() {
    let currency = document.getElementById("new-currency").value;
    currency = parseInt(currency);

    if (currency === 0) {
        insertErrorMessage(document.getElementById("editAccountForm"), "error", "Choose currency");
        return false;
    } else {
        return true;
    }
}