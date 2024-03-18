// drag
const listItems = document.querySelectorAll('li');

const connectDrag = (e, li) => {
    li.classList.add('dragging');
    e.dataTransfer.setData('text/plain', li.id);
    e.dataTransfer.effectAllowed = 'move';
};

listItems.forEach((li) =>
    li.addEventListener('dragstart', (e) => connectDrag(e, li))
);

// drop
const lists = document.querySelectorAll('ul');

lists.forEach((list) => {
    list.addEventListener('dragenter', (e) => {
        // check only accept the correct type of data
        if (e.dataTransfer.types[0] === 'text/plain') {
            e.preventDefault();
            list.classList.add('droppable');
        }
    });

    list.addEventListener('dragleave', (e) => {
        if (e.relatedTarget.closest('ul') !== list)
            list.classList.remove('droppable');
    });

    list.addEventListener('dragover', (e) => {
        // prevent default to allow drop
        if (e.dataTransfer.types[0] === 'text/plain') {
            e.preventDefault();
        }
    });

    list.addEventListener('drop', (e) => {
        const id = e.dataTransfer.getData('text/plain');
        const listArr = Array.from(list.children);
        if (listArr.find((li) => li.id === id)) return;

        const listItem = document.querySelector(`#${id}`);
        listItem.remove();
        list.appendChild(listItem);
        list.classList.remove('droppable');
    });
});