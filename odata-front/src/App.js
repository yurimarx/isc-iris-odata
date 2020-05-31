import React from 'react';
import Container from '@material-ui/core/Container';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';
import './App.css';
import ParamForm from './ParamForm';

function App() {
  return (
    <Container>
      <Box my={10}>
        <Typography variant="h4" component="h1" gutterBottom>
          OData Server for Intersystems IRIS - Set Parameters
        </Typography>
        <ParamForm/>
      </Box>
    </Container>
  );
}

export default App;
