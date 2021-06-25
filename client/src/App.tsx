import React, { useState } from 'react';
import './styles/app.scss';

import CustomInput from './components/layout/CustomInput';
import CustomButton from './components/layout/CustomButton';

const App = () => {
  const [text, setText] = useState('');

  return (
    <div>
      <h1>Hello World</h1>
      <CustomInput
        label='Hello'
        isBlock={true}
        type='password'
        value={text}
        onChange={(e: any) => setText(e.target.value)}
      />
      <CustomInput
        label='Hello'
        isBlock={true}
        type='password'
        value={text}
        onChange={(e: any) => setText(e.target.value)}
      />
      <CustomButton
        text='Test'
        onClick={() => console.log(text)}
        color='primary'
        isBlock={true}
      />
    </div>
  );
};

export default App;
