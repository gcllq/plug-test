document.addEventListener('DOMContentLoaded', () => {
    // Initial draggables setup
    document.querySelectorAll('#source .draggable').forEach(elem => {
        elem.addEventListener('dragstart', (e) => {
            e.dataTransfer.setData("text", e.target.innerText);
        });
    });

    const target = document.getElementById('target');
    let movingElem = null;
    let offsetX = 0;
    let offsetY = 0;

    target.addEventListener('dragover', (e) => {
        e.preventDefault();
    });

    target.addEventListener('drop', (e) => {
        e.preventDefault();
        const data = e.dataTransfer.getData("text");
        createDraggableElement(e.offsetX, e.offsetY, data, target);
    });

    function createDraggableElement(x, y, text, target) {
        const newElem = document.createElement("div");
        newElem.className = "draggable target-elem";
        newElem.innerText = text;
        newElem.style.left = `${x}px`;
        newElem.style.top = `${y}px`;
        target.appendChild(newElem);

        // Make new elements in target area draggable within the area
        newElem.addEventListener('mousedown', (e) => {
            movingElem = newElem;
            offsetX = e.clientX - newElem.getBoundingClientRect().left;
            offsetY = e.clientY - newElem.getBoundingClientRect().top;
            target.style.cursor = 'grabbing';
        });
    }

    document.addEventListener('mousemove', (e) => {
        if (movingElem) {
            movingElem.style.left = `${e.clientX - offsetX - target.offsetLeft}px`;
            movingElem.style.top = `${e.clientY - offsetY - target.offsetTop}px`;
        }
    });

    document.addEventListener('mouseup', () => {
        if (movingElem) {
            target.style.cursor = 'default';
            movingElem = null;
        }
    });
});
