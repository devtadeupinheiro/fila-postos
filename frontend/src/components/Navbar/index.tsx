import { Link } from "react-router-dom";
import { LinkNavbar, NavBarContainer } from "./styles";

function Navbar() {
  return (
    <NavBarContainer>
      <LinkNavbar to="/">Início</LinkNavbar>
      <LinkNavbar to="/sobre">Sobre Nós</LinkNavbar>
      <LinkNavbar to="/contato">Fale Conosco</LinkNavbar>
    </NavBarContainer>
  );
}

export default Navbar;
