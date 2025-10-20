import { Link } from "react-router-dom";
import styled from "styled-components";

export const NavBarContainer = styled.nav`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10rem;
  width: 100%;
  background-color: #28a745; /* verde */
  height: 3rem;
  border-bottom: 1px solid #218838;
`;

export const LinkNavbar = styled(Link)`
  text-decoration: none;
  color: white;
  font-weight: bold;
  padding: 0.5rem 1rem;
  border-radius: 8px;
  transition: background-color 0.3s ease;

  &:hover {
    background-color: #218838;
  }
`;
