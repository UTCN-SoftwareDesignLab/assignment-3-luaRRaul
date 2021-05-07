<template>
  <v-card>
    <v-card-title>
      Appointments
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="appointments"
      :search="search"
      @click:row="editAppointment"
    ></v-data-table>
    <DoctorsAppointmentDialog
      :opened="dialogVisible"
      :appointment="selectedAppointment"
      @refresh="refreshList"
    ></DoctorsAppointmentDialog>
  </v-card>
</template>

<script>
import api from "../api";
import DoctorsAppointmentDialog from "@/components/DoctorsAppointmentDialog";

export default {
  name: "DoctorsAppointmentList",
  components: { DoctorsAppointmentDialog },
  data() {
    return {
      appointments: [],
      search: "",
      headers: [
        { text: "Patient's name", value: "patientName" },
        { text: "Date", value: "date" },
      ],
      dialogVisible: false,
      selectedAppointment: {},
    };
  },
  methods: {
    editAppointment(appointment) {
      this.selectedAppointment = appointment;
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedAppointment = {};
      this.appointments = await api.appointments.allAppointments();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
