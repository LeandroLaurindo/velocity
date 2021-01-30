PrimeFaces.locales['pt'] = {closeText: 'Fechar', prevText: 'Anterior', nextText: 'Próximo', currentText: 'Começo', monthNames: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'], monthNamesShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'], dayNames: ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado'], dayNamesShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb'], dayNamesMin: ['D', 'S', 'T', 'Q', 'Q', 'S', 'S'], weekHeader: 'Semana', firstDay: 0, isRTL: false, showMonthAfterYear: false, yearSuffix: '', timeOnlyTitle: 'Só Horas', timeText: 'Tempo', hourText: 'Hora', minuteText: 'Minuto', secondText: 'Segundo', ampm: false, month: 'Mês', week: 'Semana', day: 'Dia', allDayText: 'Todo o Dia'};

function mascara(o, f) {
    v_obj = o;
    v_fun = f;
    setTimeout("execmascara()", 1);
}


function execmascara() {
    v_obj.value = v_fun(v_obj.value);
}

function valor(v) {
    v = v.replace(/\D/g, "");
    v = v.replace(/[0-9]{15}/, "inválido");
    v = v.replace(/(\d{1})(\d{11})$/, "$1.$2"); // coloca ponto antes dos
    // Ãºltimos 11 digitos
    v = v.replace(/(\d{1})(\d{8})$/, "$1.$2"); // coloca ponto antes dos
    // Ãºltimos 8 digitos
    v = v.replace(/(\d{1})(\d{5})$/, "$1.$2"); // coloca ponto antes dos
    // Ãºltimos 5 digitos
    v = v.replace(/(\d{1})(\d{1,2})$/, "$1,$2"); // coloca virgula antes dos
    // Ãºltimos 3 digitos
    return v;

}
function macaraKm(v) {
    dados = v.value;
    dados = dados.replace(/\D/g, "");
    dados = dados.replace(/[0-9]{15}/, "inválido");
    dados = dados.replace(/(\d{1})(\d{11})$/, "$1.$2"); // coloca ponto antes dos
    // Ãºltimos 11 digitos
    dados = dados.replace(/(\d{1})(\d{8})$/, "$1.$2"); // coloca ponto antes dos
    // Ãºltimos 8 digitos
    dados = dados.replace(/(\d{1})(\d{5})$/, "$1.$2"); // coloca ponto antes dos
    // Ãºltimos 5 digitos
    dados = dados.replace(/(\d{1})(\d{2})$/, "$1.$2"); // coloca virgula antes dos
    // Ãºltimos 3 digitos
    document.getElementById("formCadCV:kmSaida").value = dados;
}
function macaraKmr(v) {
    dados = v.value;
    dados = dados.replace(/\D/g, "");
    dados = dados.replace(/[0-9]{15}/, "inválido");
    dados = dados.replace(/(\d{1})(\d{11})$/, "$1.$2"); // coloca ponto antes dos
    // Ãºltimos 11 digitos
    dados = dados.replace(/(\d{1})(\d{8})$/, "$1.$2"); // coloca ponto antes dos
    // Ãºltimos 8 digitos
    dados = dados.replace(/(\d{1})(\d{5})$/, "$1.$2"); // coloca ponto antes dos
    // Ãºltimos 5 digitos
    dados = dados.replace(/(\d{1})(\d{2})$/, "$1.$2"); // coloca virgula antes dos
    // Ãºltimos 3 digitos
    document.getElementById("formCadCV:kmRetorno").value = dados;
}

function soNumeros(d){
 return d.replace(/\D/g,"");
}

function formatarCampo(campoTexto) {
    if (campoTexto.value.length <= 11) {
        campoTexto.value = mascaraCpf(campoTexto.value);
    } else {
        campoTexto.value = mascaraCnpj(campoTexto.value);
    }
}
function retirarFormatacao(campoTexto) {
    campoTexto.value = campoTexto.value.replace(/(\.|\/|\-)/g, "");
}
function mascaraCpf(valor) {
     valor = soNumeros(valor);
    return valor.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/g, "\$1.\$2.\$3\-\$4");
}
function mascaraCnpj(valor) {
     valor = soNumeros(valor);
    return valor.replace(/(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})/g, "\$1.\$2.\$3\/\$4\-\$5");
}

function formatarCampoCep(campoTexto) {
    campoTexto.value = mascaraCep(campoTexto.value);
}
function mascaraCep(d){
    d = soNumeros(d);
    d=d.replace(/^(\d{5})(\d)/,"$1-$2");
    return d;
}

function formatarCampoTelefone(campoTexto) {
    if (campoTexto.value.length <= 8) {
        campoTexto.value = mascaraFixo(campoTexto.value);
    } else {
        campoTexto.value = mascaraCelular(campoTexto.value);
    }
}
function mascaraCelular(valor) {
    valor = soNumeros(valor);
     valor=valor.replace(/(\d)(\d{4})$/,"$1-$2");    //Coloca hífen entre o quarto e o quinto dígitos
}
function mascaraFixo(valor) {
    valor = soNumeros(valor);
   valor=valor.replace(/(\d)(\d{4})$/,"$1-$2");    //Coloca hífen entre o quarto e o quinto dígitos
}

