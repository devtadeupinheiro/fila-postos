import React from "react";
import SpecialtyPage from "./pages/Specialty";
import Home from "./pages/Home"; // 👈 NOVO: importação da tela inicial
import Patient from "./pages/Patient"; // 👈 Tela "Sou Paciente"
import SelectQueue from "./pages/Patient/SelectQueue"; // 👈 Tela "Escolher Fila"
import SpecialyForm from "./components/Input/SpecialyForm"; // 👈 Importa o formulário
import Navbar from "./components/Navbar";

import { Routes, Route } from "react-router-dom";

// Importe seus componentes de página
// import Home from './pages/Home';
// import About from './pages/About';
// import Contact from './pages/Contact';

function App() {
  return (
    <div>
      {/* <Header />  */}
      <Navbar />

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/specialty" element={<SpecialtyPage />} />
        <Route path="/patient" element={<Patient />} /> {/* 👈 NOVO: rota da tela "Sou Paciente" */}
        <Route path="/patient/select-queue" element={<SelectQueue />} /> {/* 👈 NOVO: rota da tela "Escolher Fila" */}
        <Route path="/patient/cadastrar" element={<SpecialyForm />} /> {/* 👈 NOVO: rota para cadastro de paciente */}
        {/* <Route path="/sobre" element={<About />} /> */}

        {/* <Route path="/contato" element={<Contact />} /> */}

        {/* Rota 404 (catch-all). Renderiza se nenhuma rota acima for encontrada */}
        <Route path="*" element={<h1>Página Não Encontrada</h1>} />
      </Routes>

      {/* <Footer /> */}
    </div>
  );
}

export default App;
