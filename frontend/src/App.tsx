import React from "react";
import SpecialtyPage from "./pages/Specialty";
import Home from "./pages/Home";
import Patient from "./pages/Patient";
import SelectQueue from "./pages/Patient/SelectQueue";
import RegisterPatient from "./pages/RegisterPatient";
import Navbar from "./components/Navbar";
import QueueStatus from './pages/Patient/QueueStatus';

import { Routes, Route } from "react-router-dom";
import Admin from "./pages/Admin";
import QueuePage from "./pages/Queue";

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
        <Route path="/admin" element={<Admin />} />
        <Route path="/specialty" element={<SpecialtyPage />} />
        <Route path="/queue" element={<QueuePage />} />
        <Route path="/patient" element={<Patient />} />
        <Route path="/patient/select-queue" element={<SelectQueue />} />
        <Route path="/patient/register" element={<RegisterPatient />} />
        <Route path="/patient/queue-status" element={<QueueStatus />} />
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
