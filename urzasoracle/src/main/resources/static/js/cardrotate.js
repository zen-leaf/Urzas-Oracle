const card = document.getElementById('dragCard');


let isDragging = true;
let startX, startY;
let rotationX = 0;
let rotationY = 0;
let rotationZ = 0;
let isSideways = false;
let isClicking = false;
let velocityX = 20;
let velocityY = 0;
let momentumID = 0;
let posX = 0;
let posY = 0;
const DRAG_THRESHOLD = 10
const friction = 0.95;
const snapSpeed = 0.1;
momentumID = requestAnimationFrame(applyMomentum);


const sensitivity = 1;

// --- Core Inertia Loop ---
function applyMomentum() {

    velocityX *= friction;
    velocityY *= friction;

    rotationY += velocityX;
    rotationX -= velocityY;



    const closestFaceRotation = Math.round(rotationY / 180) * 180;

    if (Math.abs(velocityX) < 0.5 && Math.abs(velocityY) < 0.5) {
        let targetRotationY = closestFaceRotation;

        let diffY = targetRotationY - rotationY;

        rotationY += diffY * snapSpeed;

        let diffX = 0 - rotationX;
        rotationX += diffX * snapSpeed;
    }


    rotationX = Math.max(-45, Math.min(45, rotationX));

    card.style.transform = `rotateX(${rotationX}deg) rotateY(${rotationY}deg) rotateZ(${rotationZ}deg)`;

    if (Math.abs(rotationY - closestFaceRotation) > 0.01 || Math.abs(rotationX) > 0.01 || Math.abs(velocityX) > 0.1 || Math.abs(velocityY) > 0.1) {

        momentumID = requestAnimationFrame(applyMomentum);
    } else {

        cancelAnimationFrame(momentumID);
        rotationX = 0;

        rotationY = closestFaceRotation;
        card.style.transform = `rotateX(${rotationX}deg) rotateY(${rotationY}deg) rotateZ(${rotationZ}deg)`; velocityX = 0;
        velocityY = 0;
    }
    // }
}


//#region events

function handleMouseDown(e) {
    cancelAnimationFrame(momentumID);
    velocityX = 0;
    velocityY = 0;

    e.preventDefault();
    isDragging = false;
    isClicking = true;
    startX = e.clientX;
    startY = e.clientY;
    card.style.transition = 'transform 0s';
}
function handleTouchDown(e) {
    cancelAnimationFrame(momentumID);
    velocityX = 0;
    velocityY = 0;

    e.preventDefault();
    isDragging = false;
    isClicking = true;
    startX = e.targetTouches[0].clientX;
    startY = e.targetTouches[0].clientY;
    card.style.transition = 'transform 0s';
}

function handleMouseMove(e) {
    if (!e.buttons) return;
    posX = e.clientX;
    posY = e.clientY;
    handleMove(e);
    // if (isDragging) {
    //     startX = e.clientX;
    //     startY = e.clientY;
    // }
}
function handleTouchMove(e) {

    if (!isClicking && !isDragging) return;

    posX = e.targetTouches[0].clientX;
    posY = e.targetTouches[0].clientY;
    handleMove(e);
    // if (isDragging) {
    //     startX = e.targetTouches[0].clientX;
    //     startY = e.targetTouches[0].clientY;
    // }
}
function handleMove(e) {

    const deltaX = posX - startX;
    const deltaY = posY - startY;

    e.preventDefault();
    if (isClicking && (Math.abs(deltaX) > DRAG_THRESHOLD || Math.abs(deltaY) > DRAG_THRESHOLD)) {
        isClicking = false;
        isDragging = true;
    }
    if (!isDragging) return;

    velocityX = deltaX * sensitivity;
    velocityY = deltaY * sensitivity;

    rotationY += velocityX;
    rotationX -= velocityY;

    rotationY = rotationY % 360
    rotationX = Math.max(-45, Math.min(45, rotationX));

    card.style.transform = `rotateX(${rotationX}deg) rotateY(${rotationY}deg) rotateZ(${rotationZ}deg)`;
    startX = posX;
    startY = posY;
}

function handleMouseUp(e) {
    if (isClicking) {
        if ((Math.round(rotationY / 180) % 2 !== 0) || (Math.round(rotationX / 180) % 2 !== 0)) {

            rotationZ -= 90;

        } else {
            rotationZ += 90;
        }
        rotationX = Math.round(rotationX / 180) * 180;
        rotationY = Math.round(rotationY / 180) * 180;

        card.style.transition = 'transform 0.5s ease-in-out';

        isClicking = false;

    }
    isDragging = false;
    card.style.transition = 'transform 0.5s ease-out';
    applyMomentum();

}



function resetRotation() {
    cancelAnimationFrame(momentumID);
    rotationX = 0;
    rotationY = 0;
    velocityX = 0;
    velocityY = 0;
    card.style.transition = 'transform 0.5s ease-out';
    card.style.transform = `rotateX(0deg) rotateY(0deg) rotateZ(0deg)`;
    isDragging = false;
}
//#endregion events


card.addEventListener('mousedown', handleMouseDown);
window.addEventListener('mousemove', handleMouseMove);
window.addEventListener('mouseup', handleMouseUp);

card.addEventListener('touchstart', handleTouchDown);
window.addEventListener('touchmove', handleTouchMove, { passive: false });
window.addEventListener('touchend', handleMouseUp);
