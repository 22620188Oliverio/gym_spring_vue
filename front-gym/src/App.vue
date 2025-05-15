<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import Swal from 'sweetalert2';

const gym = ref([]);
const nuevocliente = ref({
  id: null,
  objetivo: '',
  nombre: '',
  apellido: '',
  telefono: '',
  email: '',
  imagenURL: '',
  tipoMembresia: ''
});

const editando = ref(false);

const cargargym = async () => {
  try {
    const response = await axios.get('http://localhost:8080/gym/cliente/traer-clientes');
    gym.value = response.data;
    console.log('Clientes cargados:', gym.value);
  } catch (error) {
    console.error('Error al cargar clientes:', error);
    alert('Error al cargar la lista de clientes');
  }
}

const agregarCliente = async () => {
  try {
    if (editando.value) {
      await axios.put(`http://localhost:8080/gym/cliente/editar-clientes/${nuevocliente.value.id}`, nuevocliente.value);
      alert('Cliente actualizado exitosamente!');
    } else {
      await axios.post('http://localhost:8080/gym/cliente/insertar-clientes', nuevocliente.value);
      alert('Cliente agregado exitosamente!');
      Swal.fire({
        title: 'Cliente agregado',
        text: 'El cliente ha sido agregado exitosamente.',
        icon: 'success',
        confirmButtonText: 'Aceptar'
      });
    }
    
    await cargargym();
    limpiarFormulario();
    editando.value = false;
  } catch (error) {
    console.error('Error al guardar cliente:', error);
    alert('Error al guardar cliente: ' + (error.response?.data?.message || error.message));
  }
};

const editarCliente = (cliente) => {
  nuevocliente.value = { ...cliente };
  editando.value = true;
  window.scrollTo({ top: 0, behavior: 'smooth' });
};

const eliminarCliente = async (id) => {
  Swal.fire({
    title: '¿Estás seguro de eliminar el cliente?',
    text: "No podrás revertir esto",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: 'Eliminar'
  }).then(async (result) => {
    if (result.isConfirmed) {
      await eliminarClienteConfirmado(id);
      Swal.fire(
        'Eliminado',
        'El cliente ha sido eliminado.',
        'success'   
      );
    }
  });
};

const eliminarClienteConfirmado = async (id) => {
  try {
    await axios.delete(`http://localhost:8080/gym/cliente/eliminar-clientes/${id}`);
    Swal.fire({
      title: 'Cliente eliminado',
      text: 'El cliente ha sido eliminado exitosamente.',
      icon: 'success',
    });
    console.log('Cliente eliminado:', id); 
    await cargargym();
  } catch (error) {
    console.error('Error al eliminar cliente:', error);
    alert('Error al eliminar cliente: ' + (error.response?.data?.message || error.message));
  }
};

const limpiarFormulario = () => {
  nuevocliente.value = {
    id: null,
    objetivo: '',
    nombre: '',
    apellido: '',
    telefono: '',
    email: '',
    imagenURL: '',
    tipoMembresia: ''
  };
};

const cancelarEdicion = () => {
  limpiarFormulario();
  editando.value = false;
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
            <div class="col-md-6 mb-3">
              <label for="nombre" class="form-label">Nombre</label>
              <input type="text" class="form-control" id="nombre" v-model="nuevocliente.nombre" required>
            </div>
            <div class="col-md-6 mb-3">
              <label for="objetivo" class="form-label">Objetivo</label>
              <input type="text" class="form-control" id="objetivo" v-model="nuevocliente.objetivo">
            </div>
            <div class="col-md-6 mb-3">
              <label for="apellido" class="form-label">Apellidos</label>
              <input type="text" class="form-control" id="apellido" v-model="nuevocliente.apellido" required>
            </div>
            <div class="col-md-6 mb-3">
              <label for="tipoMembresia" class="form-label">Tipo de membresía</label>
              <select class="form-select" id="tipoMembresia" v-model="nuevocliente.tipoMembresia" required>
                <option value="Mensual">Mensual</option>
                <option value="Trimestral">Trimestral</option>
                <option value="Anual">Anual</option>
              </select>
            </div>
            <div class="col-md-6 mb-3">
              <label for="telefono" class="form-label">Teléfono</label>
              <input type="tel" class="form-control" id="telefono" v-model="nuevocliente.telefono" required>
            </div>
            <div class="col-md-6 mb-3">
              <label for="email" class="form-label">Email</label>
              <input type="email" class="form-control" id="email" v-model="nuevocliente.email">
            </div>
            <div class="col-md-12 mb-3">
              <label for="imagenURL" class="form-label">Imagen URL</label>
              <input type="url" class="form-control" id="imagenURL" v-model="nuevocliente.imagenURL">
            </div>
          </div>
          
          <div class="d-flex justify-content-between">
            <button v-if="editando" type="button" @click="cancelarEdicion" class="btn btn-secondary">
              Cancelar
            </button>
            <div v-else></div>
            <button type="submit" class="btn btn-primary">
              {{ editando ? 'Actualizar Cliente' : 'Agregar Cliente' }}
            </button>
          </div>
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
                      <button @click="editarCliente(cliente)" class="btn btn-outline-primary" title="Editar">
                        <i class="bi bi-pencil-fill"></i>
                      </button>
                      <button @click="eliminarCliente(cliente.id)" class="btn btn-outline-danger" title="Eliminar">
                        <i class="bi bi-trash-fill"></i>
                      </button>
                    </div>
                  </td>
                </tr>
                <tr v-if="gym.length === 0">
                  <td colspan="8" class="text-center text-muted py-4">
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
.object-fit-cover {
  object-fit: cover;
}
.badge {
  font-size: 0.85em;
  padding: 0.35em 0.65em;
}
.btn-outline-primary:hover {
  color: white;
  background-color: #0d6efd;
}
.btn-outline-danger:hover {
  color: white;
  background-color: #dc3545;
}
.card {
  border-radius: 12px;
}

.card-header {
  background-color: #343a40 !important;
  color: #fff;
  font-weight: bold;
}

.btn-primary {
  background-color: #dc3545;
  border-color: #dc3545;
}
.btn-primary:hover {
  background-color: #c82333;
  border-color: #bd2130;
}

.btn-outline-primary {
  color: #dc3545;
  border-color: #dc3545;
}
.btn-outline-primary:hover {
  background-color: #dc3545;
  color: white;
}

.btn-outline-danger {
  color: #6c757d;
  border-color: #6c757d;
}
.btn-outline-danger:hover {
  background-color: #6c757d;
  color: white;
}

h2, h3 {
  color: #dc3545;
}

.table thead {
  background-color: #343a40;
  color: white;
}

.table-hover tbody tr:hover {
  background-color: #f1f1f1;
}

.badge {
  font-size: 0.85em;
  padding: 0.4em 0.7em;
}
.bg-primary {
  background-color: #dc3545 !important;
}
.bg-success {
  background-color: #28a745 !important;
}
.bg-warning {
  background-color: #ffc107 !important;
}

</style>