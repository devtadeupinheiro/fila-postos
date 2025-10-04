import { Link } from "react-router-dom";

function Navbar() {
  return (
    <nav>
      <Link to="/">Início</Link>
      <Link to="/sobre">Sobre Nós</Link>
      <Link to="/contato">Fale Conosco</Link>
    </nav>
  );
}

export default Navbar;
