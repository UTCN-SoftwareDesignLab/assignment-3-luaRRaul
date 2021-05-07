import authHeader, { BASE_URL, HTTP } from "../http";

export default {
    allAppointments() {
        return HTTP.get(BASE_URL + "/appointments", { headers: authHeader() }).then(
            (response) => {
                return response.data;
            }
        );
    },
    create(appointment) {
        return HTTP.post(BASE_URL + "/appointments", appointment, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            }
        );
    },
    edit(appointment) {
        return HTTP.put(BASE_URL + `/appointments/${appointment.id}`, appointment, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            }
        );
    },
    delete(appointment) {
        return HTTP.delete(BASE_URL + `/appointments/${appointment.id}`, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            }
        );
    },
    getFullAppointment(appointment) {
        return HTTP.get(BASE_URL + `/appointments/${appointment.id}`, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            }
        );
    },
};

