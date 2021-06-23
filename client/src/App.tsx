import React from 'react';
import './App.css';

import CustomButton from './components/layout/CustomButton';

const App = () => {
  return (
    <div>
      <h1>Hello World</h1>
      <CustomButton
        text='Test'
        onPress={() => console.log('Test')}
        type='primary'
      />
    </div>
  );
};

export default App;
