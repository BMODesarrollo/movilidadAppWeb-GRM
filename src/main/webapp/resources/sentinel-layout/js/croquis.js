var canvas = null;
var context = null;
var properties = null;
var lastX;
var lastY;
var isMousePressed = false;

window.onload = function () {

    canvas = document.getElementById("myCanvas");

    context = canvas.getContext("2d");

    var background = new Image();
    background.src = "resources/sentinel-layout/images/fondo.jpg";
    background.crossOrigin = "anonymous";

    background.onload = function () {
        context.drawImage(background, 0, 0, canvas.width, canvas.height);
    };

    properties = {
        fill: "#000000",
        stroke: "#000000",
        clear: "#FFFFFF",
        size: 2,
        cap: "round",
        join: "round"
    };


    document.ontouchmove = function (event) {
        event.preventDefault();
    };

    //document.getElementById("reset").onclick = clearAll;

    canvas.onmousedown = function (event) {
        isMousePressed = true;

        handleMouse(event, function (lastX, lastY, curX, curY) {
            point(curX, curY);
        });
    };
    canvas.onmouseup = function (event) {
        isMousePressed = false;
    };
    canvas.onmousemove = function (event) {
        if (!isMousePressed)
            return;

        handleMouse(event, function (lastX, lastY, curX, curY) {
            line(lastX, lastY, curX, curY);
        });
    };

    canvas.ontouchstart = function (event) {
        handleTouch(event, function (lastX, lastY, curX, curY) {
            point(curX, curY);
        });
    };

    canvas.ontouchmove = function (event) {
        handleTouch(event, function (lastX, lastY, curX, curY) {
            line(lastX, lastY, curX, curY);
        });
    };

    document.getElementById("yellow").onclick = function (event) {
        properties.stroke = "#fdfa00";
        properties.fill = "#fdfa00";
        properties.size = 2;
    };

    document.getElementById("blue").onclick = function (event) {
        properties.stroke = "#0c00ff";
        properties.fill = "#0c00ff";
        properties.size = 2;
    };

    document.getElementById("black").onclick = function (event) {
        properties.stroke = "#000000";
        properties.fill = "#000000";
        properties.size = 2;
    };
    document.getElementById("red").onclick = function (event) {
        properties.stroke = "#fd0000";
        properties.fill = "#fd0000";
        properties.size = 2;
    };
    document.getElementById("white").onclick = function (event) {
        properties.stroke = "#FFFFFF";
        properties.fill = "#FFFFFF";
        properties.size = 5;
    };
    document.getElementById("conver").onclick = function (event) {
        convertCanvasToImage(canvas);
    };


    if (!window.pageYOffset) {
        hideAddressBar();
    }

    window.addEventListener("orientationchange", hideAddressBar);

    clearAll();

};

function clearAll() {
    context.fillStyle = properties.clear;
    context.rect(0, 0, properties.width, properties.height);
    context.fill();
    var background = new Image();
    background.src = "resources/sentinel-layout/images/fondo.jpg";
    background.crossOrigin = "anonymous";
    background.onload = function () {
        context.drawImage(background, 0, 0, canvas.width, canvas.height);
    };
}

function doWithStyle(what) {
    context.beginPath();
    context.strokeStyle = properties.stroke;
    context.fillStyle = properties.fill;
    context.lineCap = properties.cap;
    context.lineJoin = properties.join;
    context.lineWidth = properties.size;

    what();

    context.fill();
    context.stroke();
    context.closePath();
}

function point(x, y) {
    doWithStyle(
            function () {
                context.arc(x, y, 1, 0, Math.PI * 2, true);
            }
    );
}

function line(x1, y1, x2, y2) {
    doWithStyle(
            function () {
                context.moveTo(x1, y1);
                context.lineTo(x2, y2);
            }
    );
}

function handleMouse(event, action) {
    event.preventDefault();

    var curX = event.pageX - canvas.offsetLeft;
    var curY = event.pageY - canvas.offsetTop;

    action(lastX, lastY, curX, curY);

    lastX = curX;
    lastY = curY;
}

function handleTouch(event, action) {
    event.preventDefault();

    var curX = event.touches[0].clientX - canvas.offsetLeft;
    var curY = event.touches[0].clientY - canvas.offsetTop;

    action(lastX, lastY, curX, curY);

    lastX = curX;
    lastY = curY;
}

function hideAddressBar() {
    if (!window.location.hash) {
        if (document.height < window.outerHeight) {
            document.body.style.height = (window.outerHeight + 50) + 'px';
        }

        setTimeout(function () {
            window.scrollTo(0, 1);
        }, 50);
    }
}

function convertCanvasToImage(canvas) {
    var image = new Image();
    image.crossOrigin = "anonymous";
    image.src = canvas.toDataURL("image/jpeg");
    var x = image.src;
    document.getElementById("imag").src = x;
    localStorage.clear();
    localStorage.setItem("data", x);
    window.close();
}