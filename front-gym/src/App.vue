<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const gym = ref([]);
const nuevocliente = ref({
  objetivo: '',
  nombre: '',
  apellido: '',
  telefono: '',
  email: '',
  imagenURL: '',
  tipoMembresia: ''
});

const cargargym = async () => {
  const response = await axios.get('http://localhost:8080/gym/cliente/traer-clientes');
  gym.value = response.data;
  console.log(gym.value);
}

const agregarCliente = async () => {
    await axios.post('http://localhost:8080/gym/cliente/insertar-clientes', nuevocliente.value);
    await cargargym();
    nuevocliente.value = {
      objetivo: '',
      nombre: '',
      apellido: '',
      telefono: '',
      email: '',
      imagenURL: '',
      tipoMembresia: ''
    };
};

onMounted(cargargym);

</script>

<template>
<div class="container">
  <div class="row">
    <div class="col-md-12 mt-4">
      <div class="card shadow p-4 bd-4">
        <h2 class="text-center">Bienvenido a la app de gimnasio</h2>
        <h3 class="text-center">Formulario de registro</h3>
        <form @submit.prevent="agregarCliente">
          <div class="row">
            <!-- Primera fila: Nombre y Apellidos -->
            <div class="col-md-6 mb-3">
              <label for="nombre" class="form-label">Nombre</label>
              <input type="text" class="form-control" id="nombre" v-model="nuevocliente.nombre">
            </div>
            <div class="col-md-6 mb-3">
              <label for="objetivo" class="form-label">Objetivo</label>
              <input type="text" class="form-control" id="objetivo" v-model="nuevocliente.objetivo">
            </div>
            <div class="col-md-6 mb-3">
              <label for="apellido" class="form-label">Apellidos</label>
              <input type="text" class="form-control" id="apellido" v-model="nuevocliente.apellido">
            </div>
            
            <!-- Segunda fila: Membresía y Teléfono -->
            <div class="col-md-6 mb-3">
              <label for="tipoMembresia" class="form-label">Tipo de membresía</label>
              <select class="form-select" id="tipoMembresia" v-model="nuevocliente.tipoMembresia">
                <option value="Mensual">Mensual</option>
                <option value="Trimestral">Trimestral</option>
                <option value="Anual">Anual</option>
              </select>
            </div>
            <div class="col-md-6 mb-3">
              <label for="telefono" class="form-label">Teléfono</label>
              <input type="number" class="form-control" id="telefono" v-model="nuevocliente.telefono">
            </div>
            
            <!-- Tercera fila: Imagen URL -->
            <div class="col-md-6 mb-3">
              <label for="email" class="form-label">Email</label>
              <input type="text" class="form-control" id="email" v-model="nuevocliente.email">
            </div>

            <div class="col-md-12 mb-3">
              <label for="imagenURL" class="form-label">Imagen URL</label>
              <input type="text" class="form-control" id="imagenURL" v-model="nuevocliente.imagenURL">
            </div>
          </div>
          
          <!-- Botón centrado -->
<button type="submit" class="btn btn-primary">Agregar Cliente</button>
        </form>
      </div>
    </div>
    
    <div class="col-md-12 mt-4">
      <div class="card shadow">
        <div class="card-header bg-primary text-white">
          <h5 class="mb-0">Tabla de clientes</h5>
        </div>
        <div class="card-body p-0">
          <div class="table-responsive">
            <table class="table table-hover mb-0">
              <thead class="table-light">
                <tr>
                  
                  <th scope="col">Objetivo</th>
                  <th scope="col">Nombre</th>
                  <th scope="col">Apellidos</th>
                  <th scope="col">Teléfono</th>
                  <th scope="col">Email</th>
                  <th scope="col" class="text-center">Imagen</th>
                  <th scope="col">Membresía</th>
                  <th scope="col" class="text-center">Acciones</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(cliente, index) in gym" :key="index">
                 
                  <td>{{ cliente.objetivo || 'N/A' }}</td>
                  <td>{{ cliente.nombre || 'N/A' }}</td>
                  <td>{{ cliente.apellido || 'N/A' }}</td>
                  <td>{{ cliente.telefono || 'N/A' }}</td>
                  <td>{{ cliente.email || 'N/A' }}</td>
                  <td class="text-center">
                    <img 
                      :src="cliente.imagenURL || 'https://via.placeholder.com/50'" 
                      width="50" 
                      height="50" 
                      class="rounded object-fit-cover" 
                      :alt="'Foto de '+cliente.nombre">
                  </td>
                  <td>
                    <span class="badge" :class="{
                      'bg-primary': cliente.tipoMembresia === 'Mensual',
                      'bg-success': cliente.tipoMembresia === 'Trimestral',
                      'bg-warning': cliente.tipoMembresia === 'Anual'
                    }">
                      {{ cliente.tipoMembresia || 'N/A' }}
                    </span>
                  </td>
                  <td class="text-center">
                    <div class="btn-group btn-group-sm" role="group">
                      <button class="btn btn-outline-primary" title="Editar">
                        <i class="bi bi-pencil-fill"></i>
                      </button>
                      <button class="btn btn-outline-danger" title="Eliminar">
                        <i class="bi bi-trash-fill"></i>
                      </button>
                    </div>
                  </td>
                </tr>
                <tr v-if="gym.length === 0">
                  <td colspan="9" class="text-center text-muted py-4">
                    No hay clientes registrados
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</template>

<style scoped>
/* Estilos si los necesitas */
</style>