import { Link } from "react-router-dom";
import styled from "styled-components";

export const NavBarContainer = styled.nav`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10rem;
  width: 100%;
  background-color: white;
  height: 3rem;
  border-bottom: 1px solid #ddd;
`;

export const LinkNavbar = styled(Link)`
  text-decoration: none;
  color: #007bff;
  font-weight: bold;
  transition: color 0.3s ease;

  &:hover {
    color: #0056b3;
  }
`;

