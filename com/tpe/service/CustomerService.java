package com.tpe.service;

import com.tpe.domain.Customer;
import com.tpe.dto.CustomerDTO;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    public void saveCustomer(Customer customer) {
        boolean isExistCustomer=customerRepository.existsByEmail(customer.getEmail());
        if(isExistCustomer){
            throw new ConflictException("Customer already exists by email"+customer.getEmail());
        }
        customerRepository.save(customer);
    }

    public List<Customer> getAllCustomer(){
       List<Customer> customers=customerRepository.findAll();
       return customers;
    }


    public Customer getCustomerById(Long id) {
        Customer customer=customerRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("Customer not found by id: "+id));
        return customer;
    }

    public void deleteCustomerById(Long id) {
        Customer customer=getCustomerById(id);
        customerRepository.delete(customer);
    }

    public CustomerDTO getCustomerDtoById(Long id) {//Entity->DTO
        Customer customer=getCustomerById(id);
//        CustomerDTO customerDTO=new CustomerDTO();
//        customerDTO.setName(customer.getName());
//        customerDTO.setLastName(customer.getLastName());
//        customerDTO.setEmail(customer.getEmail());
//        customerDTO.setPhone(customer.getPhone());

        CustomerDTO customerDTO=new CustomerDTO(customer);//constructorla mapleme
        return customerDTO;
    }

    public void updateCustomerById(Long id, CustomerDTO customerDTO) {
        Customer customer=getCustomerById(id);
        //email var mı
        boolean isExistsEmail=customerRepository.existsByEmail(customerDTO.getEmail());
        if(isExistsEmail && !customerDTO.getEmail().equals(customer.getEmail())){
            throw new ConflictException("Email is already in use: "+customerDTO.getEmail());
        }
        customer.setName(customerDTO.getName());
        customer.setLastName(customerDTO.getLastName());
        customer.setPhone(customerDTO.getPhone());
        customer.setEmail(customerDTO.getEmail());
        customerRepository.save(customer);
    }

    public Page<Customer> getAllCustomerByPage(Pageable pageable) {
        Page<Customer> customerPage=customerRepository.findAll(pageable);
        return customerPage;
    }

    public List<Customer> getAllCustomerByName(String name) {
        return customerRepository.findByName(name);
    }

    public List<Customer> getAllCustomerByFullName(String name, String lastName) {
        return customerRepository.findByNameAndLastName(name,lastName);
    }

    public List<Customer> getAllCustomerByNameLike(String name) {//Ja
        String lowername=name.toLowerCase();//ja
        return customerRepository.findAllByNameLike(lowername);//ja
    }
}
