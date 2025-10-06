import { Link } from "react-router-dom";
import styled from "styled-components";

export const NavBarContainer = styled.nav`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10rem;
  width: 100%;
  background-color: #49748cff;
  height: 3rem;
`;

export const LinkNavbar = styled(Link)`
  text-decoration: none;
  color: white;
`;
