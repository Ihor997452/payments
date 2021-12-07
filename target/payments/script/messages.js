let errorType = 'alert-danger';
let infoType = 'alert-info';
let successType = 'alert-success';

function insertSuccessMessage(parent, id, text) {
    let element = document.getElementById(id);
    if (element == null) {
        insertMessage(parent, id, text, successType);
    } else {
        element.parentElement.removeChild(element);
        insertMessage(parent, id, text, successType);
    }
}

function insertInfoMessage(parent, id, text) {
    let element = document.getElementById(id);

    if (element == null) {
        insertMessage(parent, id, text, infoType);
    } else {
        element.parentElement.removeChild(element);
        insertMessage(parent, id, text, errorType);
    }
}

function insertErrorMessage(parent, id, text) {
    let element = document.getElementById(id);

    if (element == null) {
        insertMessage(parent, id, text, errorType);
    } else {
        element.parentElement.removeChild(element);
        insertMessage(parent, id, text, errorType);
    }
}

function insertMessage(parent, id, text, type) {

    let insertion = document.createElement('div');
    insertion.innerText = text;
    insertion.setAttribute('id', id);
    insertion.setAttribute('class', 'alert ' + type + ' alert-dismissible fade show');
    insertion.setAttribute('role', 'alert');
    insertion.style.marginTop='5px';
    insertion.style.textTransform='capitalize';

    if (parent === document.body) {
        insertion.classList.add('fixed-bottom');
        insertion.style.width='200px';
        insertion.style.marginLeft = '10px';
    }

    let closeBtn = document.createElement('button');
    closeBtn.setAttribute('type', 'button');
    closeBtn.setAttribute('class', 'btn-close');
    closeBtn.setAttribute('data-bs-dismiss', 'alert');
    closeBtn.setAttribute('aria-label', 'Close');

    insertion.appendChild(closeBtn);
    parent.appendChild(insertion);
}
