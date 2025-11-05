import styled from 'styled-components';

export const Container = styled.div`
  padding: 2rem;
`;

export const Title = styled.h1`
  font-size: 2rem;
  margin-bottom: 1.5rem;
  color: #00796b;
`;

export const Section = styled.section`
  margin-bottom: 2rem;
`;

export const QueueCard = styled.div`
  border: 2px solid #ccc;
  padding: 1rem;
  margin-bottom: 1rem;
  border-radius: 8px;
  cursor: pointer;
  transition: border-color 0.3s ease;

  &:hover {
    border-color: #007bff;
  }
`;

export const Input = styled.input`
  padding: 0.6rem;
  margin-top: 1rem;
  width: 100%;
  border-radius: 6px;
  border: 1px solid #ccc;
  font-size: 1rem;
`;

export const Button = styled.button`
  background-color: #007bff;
  color: white;
  border: none;
  padding: 0.6rem 1.2rem;
  border-radius: 6px;
  margin-top: 1rem;
  cursor: pointer;
  font-size: 1rem;

  &:hover {
    background-color: #0056b3;
  }
`;

export const Message = styled.p`
  margin-top: 1rem;
  font-weight: bold;
  color: #007bff;
`;
