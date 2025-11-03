import styled from "styled-components";

export const Container = styled.div`
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: #f0f4f8;
`;

export const Title = styled.h1`
  font-size: 32px;
  margin-bottom: 40px;
  color: #333;
`;

export const ButtonGroup = styled.div`
  display: flex;
  gap: 20px;
`;

export const Button = styled.button`
  padding: 16px 32px;
  font-size: 18px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.3s;

  &:hover {
    background-color: #0056b3;
  }
`;

export const Logo = styled.img`
  width: clamp(140px, 25vw, 220px); /* 👈 tamanho mínimo, ideal e máximo */
  height: auto;
  display: block;
  margin: 20px auto 30px auto;
`;

