import React from "react";
import { Field, reduxForm } from "redux-form";
import Select from 'react-select';
import DatePicker from "react-datepicker";
import 'react-datepicker/dist/react-datepicker.css'

const userOptions = [
  {
    label: 'Quero Ler',
    value: 'QUERO_LER',
  },
  {
    label: 'Lendo',
    value: 'LENDO',
  },
  {
    label: 'Lido',
    value: 'LIDO',
  },
];


class BookForm extends React.Component {

  constructor(props) {
    super(props);
    if(props.initialValues?.inclusionDate)
      props.initialValues.inclusionDate = props.initialValues.inclusionDate ? new Date(props.initialValues.inclusionDate) : null
    if(props.initialValues?.dateOfTheConclusion)
      props.initialValues.dateOfTheConclusion = props.initialValues.dateOfTheConclusion ? new Date(props.initialValues.dateOfTheConclusion) : null

    this.state = {
      selectedReadedcount: props.initialValues?.status?.value == 'LIDO' ? true : false
    };
  }

  renderError({ error, touched }) {
    if (touched && error) {
      return (
        <div className="ui error message">
          <div className="header">{error}</div>
        </div>
      );
    }
  }

  renderInput = ({ input, label, meta }) => {
    const className = `field ${meta.error && meta.touched ? "error" : ""}`;
    return (
      <div className={className}>
        <label>{label}</label>
        <input {...input} autoComplete="off" />
        {this.renderError(meta)}
      </div>
    );
  };

  renderNumberInput = ({ input, label, meta }) => {
    const className = `field ${meta.error && meta.touched ? "error" : ""}`;
    return (
      <div className={className}>
        <label>{label}</label>
        <input {...input} autoComplete="off" type="number"  min="0" max="10"/>
        {this.renderError(meta)}
      </div>
    );
  };

  onSubmit = (formValues) => {
    this.props.onSubmit(formValues);
  };

  renderSelectInput = ({input, label, name, id, meta, options}) => {
    const className = `field ${meta.error && meta.touched ? "error" : ""}`;
    return (
      <div className={className}>
        <label>{label}</label>
        <Select 
          {...input}
          id={id} 
          name={name} 
          options={options}
          placeholder="Selecione o status"
          onChange={(res) => {input.onChange(res); 
          if(res.value == 'LIDO')
          this.setState({ selectedReaded: true })
          else this.setState({ selectedReaded: false })
          }}
          onBlur={() => input.onBlur(input.value)}
        />
        {this.renderError(meta)}
      </div>
    );
  };

  renderDatePicker = ({ input, meta, label, placeholder, minDate, maxDate }) => {
    const className = `field ${meta.error && meta.touched ? "error" : ""}`;
    return (
      <div className={className}>
        <label>{label}</label>
        <DatePicker
          className="plus-icon"
          dateFormat="yyyy/MM/dd"
          selected={input.value || null}
          onChange={input.onChange}
          minDate={minDate}
          maxDate={maxDate}
          disabledKeyboardNavigation
          placeholderText={placeholder}
        />
        {this.renderError(meta)}
      </div>
    );
  };

  render() {
    return (
      <form
        onSubmit={this.props.handleSubmit(this.onSubmit)}
        className="ui form error"
      >
        <Field 
          name="title" 
          component={this.renderInput} 
          label="Título" 
        />
        <Field
          name="author"
          component={this.renderInput}
          label="Autor"
        />

        <Field
            component={this.renderDatePicker}
            name="inclusionDate"
            placeholder="AAAA/MM/DD"
            label="Data em que o livro foi adicionado na lista"
        />

        <Field
            component={this.renderDatePicker}
            name="dateOfTheConclusion"
            placeholder="AAAA/MM/DD"
            label="Data de conclusão do livro"
        />

        <Field
            name="status"
            component={this.renderSelectInput}
            options={userOptions}
            clearable={false}
            label="Status"
        />

        {this.state.selectedReaded &&(
          <Field
            name="evaluationGrade"
            type="number"
            component={this.renderNumberInput}
            label="Nota de avaliação do livro"
          />)
        }

        {!this.state.selectedReaded && ( <h5>Obs: Para atribuir uma nota ao livro, mude o status para 'Lido'</h5>)}
        <button className="ui button primary">Salvar</button>
      </form>
    );
  }
}

const validate = (formValues) => {
  const errors = {};
  if (!formValues.title) {
    errors.title = "Título obrigatório";
  }
  if (!formValues.author) {
    errors.author = "Autor obrigatório";
  }
  if (!formValues.status) {
    errors.status = "Status obrigatório";
  }
  if (!formValues.inclusionDate) {
    errors.inclusionDate = "A data de inclusão do livro a esta lista é obrigatória";
  }
  
  return errors;
};

export default reduxForm({
  form: "bookForm",
  validate,
})(BookForm);
