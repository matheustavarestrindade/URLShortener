const output = document.getElementById("output");


function shortURL() {

    const slug = document.getElementById("slug");
    const url = document.getElementById("url");
    if (!url.value) {
        alert("Url cannot be empty!");
        return;
    }
    var requestURL = window.location.href + "api/create?url=" + url.value;
    if (slug.value) {
        requestURL += "&slug=" + slug.value;
    } 

    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            output.innerHTML = syntaxHighlight(JSON.stringify(JSON.parse(this.responseText), undefined, 4));
        }
    };
    xhttp.open("GET", requestURL, true);
    xhttp.send();

}


function info() {

    const slug = document.getElementById("info");
    if (!slug.value) {
        alert("Slug must be a valid value!");
        return;
    }
    var requestURL = window.location.href + "info/" + slug.value;

    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            output.innerHTML = syntaxHighlight(JSON.stringify(JSON.parse(this.responseText), undefined, 4));
        } else if (this.readyState == 4 && this.status == 400) {
            alert(JSON.parse(this.responseText).error);
        }
    };
    xhttp.open("GET", requestURL, true);
    xhttp.send();

}

function syntaxHighlight(json) {
    json = json.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
    return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function (match) {
        var cls = 'number';
        if (/^"/.test(match)) {
            if (/:$/.test(match)) {
                cls = 'key';
            } else {
                cls = 'string';
            }
        } else if (/true|false/.test(match)) {
            cls = 'boolean';
        } else if (/null/.test(match)) {
            cls = 'null';
        }
        return '<span class="' + cls + '">' + match + '</span>';
    });
}