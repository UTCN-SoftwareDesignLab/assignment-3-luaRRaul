<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
        </v-toolbar>
        <v-form>
          <v-text-field v-model="appointment.patientName" label="Patient's Name" />
          <v-text-field v-model="appointment.doctorName" label="Doctor's Name" />
          <v-text-field v-model="appointment.date" label="Date" />
          <v-text-field v-model="appointment.description" label="Description" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn @click="delet">
            Delete
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "SecretaryAppointmentDialog",
  props: {
    appointment: Object,
    opened: Boolean,
  },
  methods: {
    delet(){
      api.appointments.delete(
          {
            id: this.appointment.id,
          }
      ).then(() => this.$emit("refresh"));
    },
    persist() {
      api.appointments
          .edit({
            id: this.appointment.id,
            patientName: this.appointment.patientName,
            doctorName: this.appointment.doctorName,
            description: this.appointment.description,
            date: this.appointment.date,
          })
          .then(() => this.$emit("refresh"));
    },
  },
  watch:{
    opened: async function (newVal, oldVal) {
      if (newVal && !oldVal) {
        this.appointment = await api.appointments.getFullAppointment({
          id: this.appointment.id
        });
        this.connect();
      }
    }
  },
};
</script>

<style scoped></style>
