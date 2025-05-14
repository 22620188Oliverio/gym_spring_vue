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
  <button class="btn btn-primary">boton </button>
  </div>

</template>

<style scoped>

</style>
