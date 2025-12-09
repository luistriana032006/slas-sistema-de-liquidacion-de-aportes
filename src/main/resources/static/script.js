// =====================
// Constants
// =====================
const API_URL = '/api/slas/cotizacion';

// =====================
// DOM Elements
// =====================
const form = document.getElementById('calculatorForm');
const aporteARLCheckbox = document.getElementById('aporteARL');
const aportaCCFCheckbox = document.getElementById('aportaCCF');
const nivelRiesgoGroup = document.getElementById('nivelRiesgoGroup');
const porcentajeCCFGroup = document.getElementById('porcentajeCCFGroup');
const loading = document.getElementById('loading');
const errorMessage = document.getElementById('errorMessage');
const results = document.getElementById('results');

// =====================
// Event Listeners
// =====================
document.addEventListener('DOMContentLoaded', () => {
    // Mostrar/ocultar campos condicionales
    aporteARLCheckbox.addEventListener('change', toggleNivelRiesgo);
    aportaCCFCheckbox.addEventListener('change', togglePorcentajeCCF);

    // Manejar envío del formulario
    form.addEventListener('submit', handleSubmit);

    // Manejar reset del formulario
    form.addEventListener('reset', handleReset);

    // Smooth scroll para navegación
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            if (target) {
                target.scrollIntoView({
                    behavior: 'smooth',
                    block: 'start'
                });
            }
        });
    });
});

// =====================
// Toggle Functions
// =====================
function toggleNivelRiesgo() {
    if (aporteARLCheckbox.checked) {
        nivelRiesgoGroup.style.display = 'flex';
        document.getElementById('nivelRiesgo').required = true;
    } else {
        nivelRiesgoGroup.style.display = 'none';
        document.getElementById('nivelRiesgo').required = false;
        document.getElementById('nivelRiesgo').value = '';
    }
}

function togglePorcentajeCCF() {
    if (aportaCCFCheckbox.checked) {
        porcentajeCCFGroup.style.display = 'flex';
        document.getElementById('porcentajeCCF').required = true;
    } else {
        porcentajeCCFGroup.style.display = 'none';
        document.getElementById('porcentajeCCF').required = false;
        document.getElementById('porcentajeCCF').value = '';
    }
}

// =====================
// Form Handlers
// =====================
async function handleSubmit(e) {
    e.preventDefault();

    // Ocultar mensajes anteriores
    hideMessages();

    // Construir el objeto de request
    const requestData = buildRequestData();

    // Validar datos
    if (!validateRequest(requestData)) {
        return;
    }

    // Mostrar loading
    loading.style.display = 'block';

    try {
        // Hacer la petición a la API
        const response = await fetch(API_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(requestData)
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Error al calcular los aportes');
        }

        const data = await response.json();
        displayResults(data, requestData);

    } catch (error) {
        showError(error.message);
    } finally {
        loading.style.display = 'none';
    }
}

function handleReset() {
    hideMessages();
    results.style.display = 'none';
    nivelRiesgoGroup.style.display = 'none';
    porcentajeCCFGroup.style.display = 'none';
}

// =====================
// Data Processing
// =====================
function buildRequestData() {
    const ingresosMensual = parseFloat(document.getElementById('ingresosMensual').value);
    const aporteARL = aporteARLCheckbox.checked;
    const aportaCCF = aportaCCFCheckbox.checked;

    const requestData = {
        ingresosMensual: ingresosMensual,
        aporteARL: aporteARL,
        aportaCCF: aportaCCF
    };

    // Agregar nivel de riesgo si aplica
    if (aporteARL) {
        const nivelRiesgo = document.getElementById('nivelRiesgo').value;
        if (nivelRiesgo) {
            requestData.nivelRiesgo = nivelRiesgo;
        }
    }

    // Agregar porcentaje CCF si aplica
    if (aportaCCF) {
        const porcentajeCCF = document.getElementById('porcentajeCCF').value;
        if (porcentajeCCF) {
            requestData.porcentajeCCF = parseFloat(porcentajeCCF);
        }
    }

    return requestData;
}

function validateRequest(data) {
    // Validar ingreso
    if (!data.ingresosMensual || data.ingresosMensual <= 0) {
        showError('Por favor ingresa un valor válido para el ingreso mensual');
        return false;
    }

    // Validar ARL
    if (data.aporteARL && !data.nivelRiesgo) {
        showError('Por favor selecciona un nivel de riesgo para ARL');
        return false;
    }

    // Validar CCF
    if (data.aportaCCF && !data.porcentajeCCF) {
        showError('Por favor selecciona un porcentaje para CCF');
        return false;
    }

    return true;
}

// =====================
// Display Functions
// =====================
function displayResults(data, requestData) {
    // Actualizar valores
    document.getElementById('ibcValue').textContent = formatCurrency(data.ibc);
    document.getElementById('saludValue').textContent = formatCurrency(data.salud);
    document.getElementById('pensionValue').textContent = formatCurrency(data.pension);
    document.getElementById('fspValue').textContent = formatCurrency(data.fsp);

    // Mostrar/ocultar aportes voluntarios
    const arlResult = document.getElementById('arlResult');
    const ccfResult = document.getElementById('ccfResult');

    if (requestData.aporteARL && data.arl > 0) {
        document.getElementById('arlValue').textContent = formatCurrency(data.arl);
        arlResult.style.display = 'flex';
    } else {
        arlResult.style.display = 'none';
    }

    if (requestData.aportaCCF && data.ccf > 0) {
        document.getElementById('ccfValue').textContent = formatCurrency(data.ccf);
        ccfResult.style.display = 'flex';
    } else {
        ccfResult.style.display = 'none';
    }

    // Mostrar total
    document.getElementById('totalValue').textContent = formatCurrency(data.total);

    // Mostrar sección de resultados
    results.style.display = 'block';

    // Scroll suave hacia los resultados
    results.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
}

function formatCurrency(value) {
    return new Intl.NumberFormat('es-CO', {
        style: 'currency',
        currency: 'COP',
        minimumFractionDigits: 0,
        maximumFractionDigits: 0
    }).format(value);
}

// =====================
// Message Functions
// =====================
function showError(message) {
    errorMessage.textContent = message;
    errorMessage.style.display = 'block';
    errorMessage.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
}

function hideMessages() {
    errorMessage.style.display = 'none';
    errorMessage.textContent = '';
}
