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
          <v-text-field v-model="appointment.patientName" label="Patient Name" />
          <v-text-field v-model="appointment.date" label="Date" />
        </v-form>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "DoctorsAppointmentDialog",
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
              date: this.appointment.date,
            })
            .then(() => this.$emit("refresh"));
    },
  },
};
</script>

<style scoped></style>
