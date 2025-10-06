import React from "react";
import SpecialyPage from "./pages/Specialty";
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
        <Route path="/" element={<SpecialyPage />} />

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
