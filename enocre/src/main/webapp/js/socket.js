// 웹소켓을 지정한 url로 연결한다.

let sock = new SockJS("/myHandler.do");

sock.onopen = function() {
    console.log('open');
    sock.send('test');
};

sock.onmessage = function(e) {
    console.log('message', e.data);
    sock.close();
};

sock.onclose = function() {
    console.log('close');
};