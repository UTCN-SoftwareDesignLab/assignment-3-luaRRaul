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
          <v-text-field v-model="appointment.patient.firstName" label="Patient's First Name" />
          <v-text-field v-model="appointment.patient.lastName" label="Patient's Last Name" />
          <v-text-field v-model="appointment.description" label="Description" />
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
              firstName: this.appointment.patient.firstName,
              lastName: this.appointment.patient.lastName,
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
