import React from 'react';
import './styles/app.scss';

import CustomInput from './components/layout/CustomInput';
import CustomButton from './components/layout/CustomButton';

const App = () => {
  return (
    <div>
      <h1>Hello World</h1>
      <CustomInput label='Hello' isBlock={true} />
      <CustomButton
        text='Test'
        onClick={() => console.log('Test')}
        color='primary'
        isBlock={true}
      />
    </div>
  );
};

export default App;
