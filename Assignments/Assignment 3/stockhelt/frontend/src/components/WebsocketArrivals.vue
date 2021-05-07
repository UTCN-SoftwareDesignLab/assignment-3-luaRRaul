<template>
  <div>
  </div>
</template>
<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
export default {
  name: "websocketdemo",
  data() {
    return {
      received_messages: [],
      send_message: null,
      connected: false
    };
  },
  methods: {
    send() {
      console.log("Send message:" + this.send_message);
      if (this.stompClient && this.stompClient.connected) {
        this.send_message = { patientId : this.send_message };
        console.log(JSON.stringify(this.send_message));
        this.stompClient.send("/api/ws/arrival", JSON.stringify(this.send_message),{});
      }
    },
    connect() {
      this.socket = new SockJS("http://localhost:8088/api/my-websocket");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect(
          {},
          frame => {
            this.connected = true;
            console.log(frame);
            this.stompClient.subscribe("/api/topic/arrivals", tick => {
              console.log(tick);
              this.received_messages.push(JSON.parse(tick.body).connect());
            });
          },
          error => {
            console.log(error);
            this.connected = false;
          }
      );
    },
    disconnect() {
      if (this.stompClient) {
        this.stompClient.disconnect();
      }
      this.connected = false;
    },
    tickleConnection() {
      this.connected ? this.disconnect() : this.connect();
    }
  },
  mounted() {
    this.connect();
  }
};
</script>
<style scoped="">
</style>
