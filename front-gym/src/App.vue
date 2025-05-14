<script setup>
import{ref, onMounted} from 'vue';
import axios from 'axios';

const gym = ref([]);
const cargargym = async () => {
 const response = await axios.get('http://localhost:8080/gym/cliente/traer-clientes');
 gym.value = response.data;
  console.log(gym.value);
}

onMounted(cargargym);
</script>

<template>
<div class="container">
<h2>Tabla de clientes</h2>
<table class="table">
  <thead>
    <tr>
      <th scope="col">ID</th>
      <th scope="col">Número membresia</th>
      <th scope="col">Nombre</th>
      <th scope="col">Apellidos</th>
      <th scope="col">Telefono</th>
      <th scope="col">Email</th>
      <th scope="col">Imagen URL</th>
      <th scope="col">Tipo de membresia</th>
      <th scope="col">Acciones</th>
    </tr>
  </thead>
  <tbody>
    <tr v-for="(cliente, index) in gym" :key="index">
      <td>{{ cliente.id }}</td>
      <td>{{ cliente.numeroMembresia }}</td>
      <td>{{ cliente.nombre }}</td>
      <td>{{ cliente.apellidos }}</td>
      <td>{{ cliente.telefono }}</td>
      <td>{{ cliente.email }}</td>
      <td>{{ cliente.imagenURL }}</td>
      <td>{{ cliente.tipoMembresia }}</td>
      <td>
        <button class="btn btn-primary">Editar</button>
        <button class="btn btn-danger">Eliminar</button>
      </td>
    </tr>
  </tbody>
</table> 
</div>

</template>

<style scoped>

</style>
