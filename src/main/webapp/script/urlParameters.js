function addUrlParameter(parameter, value) {
    let params = new URLSearchParams(window.location.search);
    params.set(parameter, value);
    window.location.search = params.toString();
}