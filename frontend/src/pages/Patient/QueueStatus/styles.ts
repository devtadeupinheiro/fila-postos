import styled from 'styled-components';

export const Container = styled.div`
  padding: 2rem;
`;

export const Title = styled.h1`
  font-size: 2rem;
  margin-bottom: 1rem;
  color: #00796b;
`;

export const Input = styled.input`
  padding: 0.6rem;
  width: 100%;
  max-width: 400px;
  margin-bottom: 1rem;
  border-radius: 6px;
  border: 1px solid #ccc;
  font-size: 1rem;
`;

export const OptionButton = styled.button`
  background-color: #007bff;
  color: white;
  border: none;
  padding: 0.6rem 1.2rem;
  border-radius: 6px;
  margin-bottom: 2rem;
  cursor: pointer;
  font-size: 1rem;

  &:hover {
    background-color: #0056b3;
  }
`;

export const InfoBox = styled.div`
  margin-bottom: 2rem;
`;

export const ErrorMessage = styled.p`
  color: red;
  font-weight: bold;
  margin-bottom: 1rem;
`;

export const QueueCard = styled.div`
  border: 1px solid #ccc;
  padding: 1rem;
  margin-bottom: 1rem;
  border-radius: 8px;
  background-color: #f9f9f9;
`;
