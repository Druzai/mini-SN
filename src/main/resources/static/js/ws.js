var stompClient = null;

function connect() {
    var socket = new SockJS('/message-ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages', function (message) {
            if (JSON.parse(message.body).toUser === document.getElementById("orig_name").innerText) {
                let dict = JSON.parse(message.body);
                let div = document.getElementById("dialogList");
                let innerDiv = document.createElement('div');
                innerDiv.className = 'mt-2 card bg-light justify-content-center align-items-center';
                let b = document.createElement("b");
                b.innerText = dict.fromUser;
                let p = document.createElement("p");
                p.innerText = dict.content;
                innerDiv.appendChild(b);
                innerDiv.appendChild(p);
                div.appendChild(innerDiv);
                window.scrollTo(0, document.body.scrollHeight);
            }
        });
    });
}

function sendName() {
    stompClient.send("/app/send", {}, JSON.stringify({
        'toUser': document.getElementById("name").innerText,
        'fromUser': document.getElementById("orig_name").innerText,
        'content': document.getElementById("content").value
    }));
}

$(function () {
    $("#send_button").click(function () {
        sendName();
    });
});

connect();
window.scrollTo(0, document.body.scrollHeight);