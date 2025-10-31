function reformatText() {
    console.log("lool");
    var replaceCandidates = document.querySelectorAll(".replaceCandidate")
    console.log(replaceCandidates);
    replaceCandidates.forEach(e => {
        console.log(e.innerHTML);
        var originalText = e.innerHTML;

        var editedText = originalText.replace(/\{([^}]+)\}/g, (value, content) => {
            var wrapperIcon = `<abbr class="card-symbol card-symbol-${content}">${value}</abbr>`;
            return wrapperIcon;
        })
        e.innerHTML = editedText;
        e.classList.remove("replaceCandidate");
    });


}
document.addEventListener('DOMContentLoaded', () => { reformatText() })
