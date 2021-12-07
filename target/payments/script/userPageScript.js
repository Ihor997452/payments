function validateTransactionForm() {
    let senderInfo = document.getElementById("account-sender").value;

    if (senderInfo === "0") {
        insertErrorMessage(document.body, "error", "Choose one of your accounts");
        return false;
    }

    senderInfo = senderInfo.split(";");
    let senderId = parseInt(senderInfo[0]);
    let senderBalance = parseInt(senderInfo[1]);
    let amount = parseInt(document.getElementById("amount").value);
    let recipient = parseInt(document.getElementById("recipient").value);

    if (senderId === recipient) {
        insertErrorMessage(document.body, "error", "Sender and recipient must be different");
        return false;
    }

    if (senderBalance < amount) {
        insertErrorMessage(document.body, "error", "Balance is lower than transaction amount");
        return false;
    }

    return true;
}

function validateEditForm() {
    let password = document.getElementById("password-edit").value;

    if (password.length < 6) {
        insertErrorMessage(document.getElementById("editProfileForm"), "error", "Password length >= 6");
        return false;
    } else {
        return true;
    }
}

function validateNewAccountForm() {
    let currency = document.getElementById("account-currency").value;
    currency = parseInt(currency);

    if (currency === 0) {
        insertErrorMessage(document.getElementById("newAccountForm"), "error", "Choose currency");
        return false;
    } else {
        return true;
    }
}

function validateTopUpForm() {
    let amount = document.getElementById("top-up-amount").value;
    let currency = document.getElementById("top-up-currency").value;
    currency = parseInt(currency);
    amount = parseInt(amount);

    console.log(currency);
    console.log(amount);


    if (currency === 0) {
        insertErrorMessage(document.getElementById("topUpForm"), "error", "Choose currency");
        return false;
    } else if (amount <= 0) {
        insertErrorMessage(document.getElementById("topUpForm"), "error", "Invalid amount");
        return false;
    } else {
        return true;
    }
}