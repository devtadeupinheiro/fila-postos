import styled from 'styled-components';

export const Container = styled.div`
  min-height: 100vh;
  background-color: #ffffff; /* ← fundo original mantido */
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 2rem;
`;

export const Title = styled.h1`
  font-size: 2.5rem;
  color: #00796b;
  margin-bottom: 2rem;
`;

export const InfoBox = styled.div`
  background-color: #ffffff;
  border: 2px solid #00796b;
  border-radius: 12px;
  padding: 2rem;
  width: 100%;
  max-width: 400px;
  text-align: center;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);

  p {
    font-size: 1.2rem;
    color: #333;

    strong {
      font-size: 1.5rem;
      color: #00796b;
    }
  }
`;

export const ErrorMessage = styled.p`
  color: #d32f2f;
  font-size: 1.2rem;
  font-weight: bold;
  background-color: #ffebee;
  padding: 1rem;
  border-radius: 8px;
  max-width: 400px;
  text-align: center;
`;

export const OptionButton = styled.button`
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 8px;
  padding: 0.8rem 1.5rem;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
  margin-top: 2rem;

  &:hover {
    background-color: #0056b3;
  }
`;
