package com.ecommercesystem.backend.controller;

import com.ecommercesystem.backend.exception.ResourceNotFoundException;
import com.ecommercesystem.backend.model.Item;
import com.ecommercesystem.backend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin //remove in production - bypass CORS policy error
@RequestMapping("/api/v1/items")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("")
    public ResponseEntity<List<Item>> getAllItems(){
        return ResponseEntity.ok(itemRepository.findAll());
    }


    @PostMapping("/newItem")
    public ResponseEntity<Item> createItem(@RequestBody Item item){
        return ResponseEntity.ok(itemRepository.save(item));
    }

    @GetMapping("{id}")
    public ResponseEntity<Item> getItemById(@PathVariable("id") long id){
        Item item = itemRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("No item found to the given item id: " + id)
        );
        return ResponseEntity.ok(item);
    }

    @PutMapping("{id}")
    public ResponseEntity<Item> updateItem(@PathVariable("id") long id, @RequestBody Item editedItem){
        Item itemToUpdate = itemRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("No item found to the given item id: " + id)
        );

        itemToUpdate.setName(editedItem.getName());
        itemToUpdate.setDescription(editedItem.getDescription());
        itemToUpdate.setPrice(editedItem.getPrice());
        itemToUpdate.setQuantity(editedItem.getQuantity());
        itemToUpdate.setCategory(editedItem.getCategory());

        itemRepository.save(itemToUpdate);

        return ResponseEntity.ok(itemToUpdate);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id){
        Item item = itemRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("No item found to the given item id: " + id)
        );
        itemRepository.delete(item);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}