<template>
  <v-card>
    <v-card-title>
      Patients
      <v-spacer></v-spacer>
      <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Search"
          single-line
          hide-details
      ></v-text-field>
      <v-btn @click="createPatient">Create Patient</v-btn>
      <v-btn @click="goAppointments">Appointment List</v-btn>
    </v-card-title>
    <v-data-table
        :headers="headers"
        :items="patients"
        :search="search"
        @click:row="editPatient"
    ></v-data-table>
    <PatientDialog
        :opened="dialogVisible"
        :patient="selectedPatient"
        @refresh="refreshList"
    ></PatientDialog>
  </v-card>
</template>

<script>
import api from "../api";
import PatientDialog from "@/components/PatientDialog";
import router from "@/router";

export default {
  name: "PatientList",
  components: { PatientDialog},
  data() {
    return {
      patients: [],
      search: "",
      headers: [
        { text: "First Name", value: "firstName" },
        { text: "Last Name", value: "lastName" },
        { text: "CNP", value: "cnp" },
      ],
      dialogVisible: false,
      selectedPatient: {},
    };
  },
  methods: {
    goAppointments(){
      router.push("/secretaryAppointmentList")
    },
    createPatient() {
      this.dialogVisible = true;
    },
    editPatient(patient) {
      this.selectedPatient = patient;
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedPatient = {};
      this.patients = await api.patients.allPatients();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
