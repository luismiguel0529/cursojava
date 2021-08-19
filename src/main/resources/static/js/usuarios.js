// Call the dataTables jQuery plugin
$(document).ready(function() {
  cargarUsuario();
  $('#usuarios').DataTable();
  actualizarEmailUsuario();
});

function actualizarEmailUsuario(){
    document.getElementById('txt-email-usuario').outerHTML = localStorage.email;
}

async function cargarUsuario(){

   const request = await fetch("api/usuarios",{
        method: 'GET',
        headers: getHeader()
   });

   const response = await request.json();

   console.log(response);

    let listaUsuariosHtml = '';

   for(let usuario of response){
        let botonEliminar = '<a href="#" onclick="eliminarUsuario('+usuario.id+')" class="btn btn-danger btn-circle"><i class="fas fa-trash"></i></a>';

        let telefono = usuario.telefono == null ? '-' : usuario.telefono;

        let usuarioHtml = '<tr><td>'+usuario.id+'</td><td>'
        +usuario.nombre+' '+usuario.apellido+'</td><td>'
        +usuario.email+'</td><td>'
        +telefono+'</td><td>'+botonEliminar+'</td></tr>';
        listaUsuariosHtml += usuarioHtml;
   }

   document.querySelector('#usuarios tbody').outerHTML = listaUsuariosHtml;
}

async function eliminarUsuario(id){

    if(!confirm('Desea eliminar este usuario?')){
        return;
    }
   const request = await fetch("api/usuarios/" + id,{
        method: 'DELETE',
        headers: getHeader()
   });

   location.reload();
}

function getHeader(){
            return {
                       'Accept': 'application/json',
                       'Content-Type': 'application/json',
                       'Authorization': localStorage.token
                   }
}