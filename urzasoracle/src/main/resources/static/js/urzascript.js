function reformatText() {
    var replaceCandidates = document.querySelectorAll(".replaceCandidate")
    replaceCandidates.forEach(e => {
        var originalText = e.innerHTML;

        var editedText = originalText.replace(/\{([^}]+)\}/g, (value, content) => {

            content = content.replace("/", "")
            var wrapperIcon = `<abbr class="card-symbol card-symbol-${content}">${value}</abbr>`;
            return wrapperIcon;
        })
        e.innerHTML = editedText;
        e.classList.remove("replaceCandidate");
    });


}

document.addEventListener('DOMContentLoaded', () => { reformatText() })
