<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create patient" : "Edit patient" }}
        </v-toolbar>
        <v-form >
          <v-text-field v-model="patient.firstName" label="First Name" />
          <v-text-field v-model="patient.lastName" label="Last Name" />
          <v-text-field v-model="patient.email" label="Email" />
          <v-text-field v-model="patient.cnp" label="CNP" />
          <v-text-field v-model="patient.dateOfBirth" label="Date of Birth" />
          <v-text-field v-model="patient.address" label="Address" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn @click="delet">
            Delete
          </v-btn>
          <v-btn @click="send">
            Arrived
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
  name: "PatientDialog",
  data(){
    return {
    send_message: null,
    connected: false,
      // roles: ["Secretary", "Doctor", "Admin"],
    }
  },
  props: {
    patient: Object,
    opened: Boolean,
  },
  methods: {
    delet(){
      api.patients.delete({
        id: this.patient.id,
      }).then(() => this.$emit("refresh"));
    },
    persist() {
      if (this.isNew) {
        api.patients
            .create({
              firstName: this.patient.firstName,
              lastName: this.patient.lastName,
              email: this.patient.email,
              cnp: this.patient.cnp,
              dateOfBirth: this.patient.dateOfBirth,
              address: this.patient.address,
            })
            .then(() => this.$emit("refresh"));
      } else {
        api.patients
            .edit({
              id: this.patient.id,
              firstName: this.patient.firstName,
              lastName: this.patient.lastName,
              email: this.patient.email,
              cnp: this.patient.cnp,
              dateOfBirth: this.patient.dateOfBirth,
              address: this.patient.address,
            })
            .then(() => this.$emit("refresh"));
      }
    },
    send() {
      console.log("Send message:" + this.send_message);
      if (this.stompClient && this.stompClient.connected) {
        this.send_message = { patientId : this.patient.id };
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
          },
          error => {
            console.log(error);
            this.connected = false;
          }
      );
    },
  },
  computed: {
    isNew: function () {
      return !this.patient.id;
    },
  },
  watch:{
    opened: async function (newVal, oldVal) {
      if (newVal && !oldVal) {
        this.patient = await api.patients.getFullPatient({
          id: this.patient.id
        });
        this.connect();
      }
    }
  },
};
</script>

<style scoped></style>
