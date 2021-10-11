var stompClient = null;

function connect() {
    var socket = new SockJS('/message-ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages', function (message) {
            if (JSON.parse(message.body).content === $("#orig_name")[0].innerText) {
                window.location.reload(); // Reload page when user send new message
            }
        });
    });
}

function sendName() {
    stompClient.send("/app/send", {}, JSON.stringify({'content': $("#name")[0].innerText}));
}

$(function () {
    $("#send_button").click(function () {
        sendName();
    });
});

connect();
window.scrollTo(0,document.body.scrollHeight);